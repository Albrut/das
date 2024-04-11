import java.util.*;

class BankBranch {
    private int branchNumber;
    private String address;

    public BankBranch(int branchNumber, String address) {
        this.branchNumber = branchNumber;
        this.address = address;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Branch Number: " + branchNumber + ", Address: " + address;
    }
}

class QueueReservation {
    private int clientId;
    private int branchNumber;
    private int queueNumber;

    public QueueReservation(int clientId, int branchNumber, int queueNumber) {
        this.clientId = clientId;
        this.branchNumber = branchNumber;
        this.queueNumber = queueNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    @Override
    public String toString() {
        return "Client ID: " + clientId + ", Branch Number: " + branchNumber + ", Queue Number: " + queueNumber;
    }
}

class ClientRedirection {
    private int clientId;
    private int oldEmployeeId;
    private int newEmployeeId;

    public ClientRedirection(int clientId, int oldEmployeeId, int newEmployeeId) {
        this.clientId = clientId;
        this.oldEmployeeId = oldEmployeeId;
        this.newEmployeeId = newEmployeeId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getOldEmployeeId() {
        return oldEmployeeId;
    }

    public int getNewEmployeeId() {
        return newEmployeeId;
    }

    @Override
    public String toString() {
        return "Client ID: " + clientId + ", Old Employee ID: " + oldEmployeeId + ", New Employee ID: " + newEmployeeId;
    }
}


class Analytics {
    public void analyzeProcessingTime(List<QueueReservation> reservations, List<BankBranch> branches) {
        System.out.println("Анализ времени обработки:");

        Map<Integer, Integer> waitingTimesPerBranch = new HashMap<>();
        int totalWaitingTime = 0;
        int totalReservations = 0;

        for (QueueReservation reservation : reservations) {
            totalWaitingTime += reservation.getQueueNumber();
            totalReservations++;

            int branchNumber = reservation.getBranchNumber();
            if (waitingTimesPerBranch.containsKey(branchNumber)) {
                waitingTimesPerBranch.put(branchNumber, waitingTimesPerBranch.get(branchNumber) + reservation.getQueueNumber());
            } else {
                waitingTimesPerBranch.put(branchNumber, reservation.getQueueNumber());
            }
        }

        System.out.println("Среднее время ожидания:");
        for (BankBranch branch : branches) {
            int branchNumber = branch.getBranchNumber();
            if (waitingTimesPerBranch.containsKey(branchNumber)) {
                int avgWaitingTime = waitingTimesPerBranch.get(branchNumber) / totalReservations;
                System.out.println("Филиал " + branchNumber + ": " + avgWaitingTime + " мин");
            } else {
                System.out.println("Филиал " + branchNumber + ": нет данных");
            }
        }

        int totalAvgWaitingTime = totalWaitingTime / totalReservations;
        System.out.println("Общее среднее время ожидания: " + totalAvgWaitingTime + " мин");
    }

    public void analyzeOperationTypes(List<QueueReservation> reservations, List<BankBranch> branches) {
        System.out.println("Анализ по видам операций и услугам:");

        Map<Integer, Integer> operationCounts = new HashMap<>();

        for (QueueReservation reservation : reservations) {
            int operationType = determineOperationType(reservation);
            if (operationCounts.containsKey(operationType)) {
                operationCounts.put(operationType, operationCounts.get(operationType) + 1);
            } else {
                operationCounts.put(operationType, 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : operationCounts.entrySet()) {
            System.out.println("Тип операции " + entry.getKey() + ": " + entry.getValue() + " бронирований");
        }
    }

    private int determineOperationType(QueueReservation reservation) {
        // Загенерим по приколу, т.к. чёрт его знает как это сделать
        return new Random().nextInt(5) + 1; // Ну, у меня есть что-то вроде 5 операций
    }

    public void analyzeEmployees(List<QueueReservation> reservations, List<BankBranch> branches) {
        System.out.println("Анализ по сотрудникам и отделениям банка:");

        Map<Integer, Map<Integer, Integer>> employeeCountsPerBranch = new HashMap<>();

        for (QueueReservation reservation : reservations) {
            int branchNumber = reservation.getBranchNumber();
            int employeeId = determineEmployeeId(reservation);

            if (!employeeCountsPerBranch.containsKey(branchNumber)) {
                employeeCountsPerBranch.put(branchNumber, new HashMap<>());
            }

            Map<Integer, Integer> employeeCounts = employeeCountsPerBranch.get(branchNumber);
            if (employeeCounts.containsKey(employeeId)) {
                employeeCounts.put(employeeId, employeeCounts.get(employeeId) + 1);
            } else {
                employeeCounts.put(employeeId, 1);
            }
        }

        for (Map.Entry<Integer, Map<Integer, Integer>> branchEntry : employeeCountsPerBranch.entrySet()) {
            int branchNumber = branchEntry.getKey();
            System.out.println("Филиал " + branchNumber + ":");
            Map<Integer, Integer> employeeCounts = branchEntry.getValue();
            for (Map.Entry<Integer, Integer> employeeEntry : employeeCounts.entrySet()) {
                int employeeId = employeeEntry.getKey();
                int reservationCount = employeeEntry.getValue();
                System.out.println("   Сотрудник " + employeeId + ": " + reservationCount + " бронирований");
            }
        }
    }

    private int determineEmployeeId(QueueReservation reservation) {
        // Здесь также должна быть реальная логика определения сотрудника
        // В этом примере мы просто вернем случайное число
        return new Random().nextInt(10) + 1; // предположим, у нас есть 10 различных сотрудников
    }
}


public class Main {
    public static void main(String[] args) {
        List<BankBranch> branches = new ArrayList<>();
        branches.add(new BankBranch(1, "Адрес филиала 1"));
        branches.add(new BankBranch(2, "Адрес филиала 2"));

        List<QueueReservation> reservations = new ArrayList<>();
        reservations.add(new QueueReservation(1, 1, 1));
        reservations.add(new QueueReservation(2, 2, 1));

        List<ClientRedirection> redirections = new ArrayList<>();
        redirections.add(new ClientRedirection(1, 1, 2));

        Analytics analytics = new Analytics();
        analytics.analyzeProcessingTime(reservations, branches);
        analytics.analyzeOperationTypes(reservations, branches);
        analytics.analyzeEmployees(reservations, branches);

        System.out.println("Банковские филиалы:");
        for (BankBranch branch : branches) {
            System.out.println(branch);
        }

        System.out.println("Зарегистрированные бронирования:");
        for (QueueReservation reservation : reservations) {
            System.out.println(reservation);
        }

        System.out.println("Перенаправления клиентов:");
        for (ClientRedirection redirection : redirections) {
            System.out.println(redirection);
        }
    }
}
