package bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MyProgram {
    private static final int TEST_NUM = 1000;
    private static final int TEST_REP = 10;
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String CITY = "city";
    private static final String RANGE = "range";
    private ArrayList<Account> accounts;

    public MyProgram() {
        this.accounts = new ArrayList<>();
    }

    public static void main(String[] args) {
        Bank bank = new Bank("Test Bank");
        Bank2 bank2 = new Bank2("Test Bank2");
        MyProgram myProgram = new MyProgram();
        myProgram.generateAccounts();
        // myProgram.timeTest(bank, bank2);
        myProgram.automatedTest(bank, bank2);
    }

    public void automatedTest(Bank bank, Bank2 bank2) {
        System.out.println("Automated test started");

        System.out.println("\nTesting Bank: add account (valid)");
        for (Account acc : accounts) {
            addAccount(bank, acc);
        }

        System.out.println("\nTesting Bank2: add account (valid)");
        for (Account acc : accounts) {
            addAccount(bank2, acc);
        }

        System.out.println("\nTesting Bank: add account (duplicate)");
        for (Account acc : accounts) {
            addAccount(bank, acc);
        }

        System.out.println("\nTesting Bank2: add account (duplicate)");
        for (Account acc : accounts) {
            addAccount(bank2, acc);
        }

        System.out.println("\nTesting Bank: find account (valid)");
        int randomId = (int) (Math.random() * TEST_NUM);
        findAccount(bank, randomId);

        System.out.println("\nTesting Bank2: find account (valid)");
        int randomId2 = (int) (Math.random() * TEST_NUM);
        findAccount(bank2, randomId2);

        System.out.println("\nTesting Bank: find account (invalid)");
        findAccount(bank, TEST_NUM + 1);

        System.out.println("\nTesting Bank2: find account (invalid)");
        findAccount(bank2, TEST_NUM + 1);

        System.out.println("\nTesting Bank: city aggregation");
        ArrayList<String> cities = bank.populateDistinctCityList();
        ArrayList<Double> balances = bank.getTotalBalancePerCity(cities);
        ArrayList<Integer> counts = bank.getTotalCountPerCity(cities);
        bank.reportTotalPerCity(cities, counts, balances);

        System.out.println("\nTesting Bank2: city aggregation");
        HashMap<String, Double> balances2 = bank2.getTotalBalancePerCity();
        HashMap<String, Integer> counts2 = bank2.getTotalCountPerCity();
        bank2.reportCity(balances2, counts2);

        System.out.println("\nTesting Bank: range aggregation");
        Integer[] r = { 1, 1000, 10000, 100000, 10000000 };
        ArrayList<Integer> ranges = new ArrayList<>(Arrays.asList(r));
        ArrayList<Integer> countsPerRange = bank.getTotalCountPerRange(ranges);
        bank.reportRanges(ranges, countsPerRange);

        System.out.println("\nTesting Bank2: range aggregation");
        HashMap<Integer, Integer> countsPerRange2 = bank2.getTotalCountPerRange(ranges);
        bank2.reportRanges(ranges, countsPerRange2);

        System.out.println("Automated test finished");
    }

    public void timeTest(Bank bank, Bank2 bank2) {
        for (int i = 0; i < TEST_REP; i++) {
            testBank(bank);
            testBank2(bank2);
        }
    }

    public void generateAccounts() {
        for (int i = 0; i < TEST_NUM; i++) {
            accounts.add(createRandomAccounts());
        }
    }

    public void testBank(Bank bank) {
        long begin = getTime();
        for (Account acc : accounts) {
            bank.addAccount(acc);
        }
        long finish = getTime();
        long addAccountTime = calcTime(begin, finish);

        begin = getTime();
        for (int i = 1; i <= TEST_NUM; i++) {
            bank.findAccount(i);
        }
        finish = getTime();
        long findAccountTime = calcTime(begin, finish);

        begin = getTime();
        ArrayList<String> cities = bank.populateDistinctCityList();
        ArrayList<Double> balances = bank.getTotalBalancePerCity(cities);
        ArrayList<Integer> counts = bank.getTotalCountPerCity(cities);
        finish = getTime();
        long cityAggregationTime = calcTime(begin, finish);

        begin = getTime();
        Integer[] r = { 1, 1000, 10000, 100000, 10000000 };
        ArrayList<Integer> ranges = new ArrayList<Integer>(Arrays.asList(r));
        ArrayList<Integer> countsPerRange = bank.getTotalCountPerRange(ranges);
        finish = getTime();
        long rangeAggregationTime = calcTime(begin, finish);

        System.out.println("-------------------Bank---------------------");
        printTime(addAccountTime, ADD);
        printTime(findAccountTime, FIND);
        printTime(cityAggregationTime, CITY);
        printTime(rangeAggregationTime, RANGE);
    }

    public void testBank2(Bank2 bank2) {
        long begin = getTime();
        for (Account acc : accounts) {
            bank2.addAccount(acc);
        }
        long finish = getTime();
        long addAccountTime = calcTime(begin, finish);

        begin = getTime();
        for (int i = 1; i <= TEST_NUM; i++) {
            bank2.findAccount(i);
        }
        finish = getTime();
        long findAccountTime = calcTime(begin, finish);

        begin = getTime();
        HashMap<String, Double> cities2 = bank2.getTotalBalancePerCity();
        HashMap<String, Integer> counts2 = bank2.getTotalCountPerCity();
        finish = getTime();
        long cityAggregationTime = calcTime(begin, finish);

        begin = getTime();
        Integer[] r = { 1, 1000, 10000, 100000, 10000000 };
        ArrayList<Integer> ranges = new ArrayList<Integer>(Arrays.asList(r));
        HashMap<Integer, Integer> countsPerRange1 = bank2.getTotalCountPerRange(ranges);
        finish = getTime();
        long rangeAggregationTime = calcTime(begin, finish);

        System.out.println("-------------------Bank 2-------------------");
        printTime(addAccountTime, ADD);
        printTime(findAccountTime, FIND);
        printTime(cityAggregationTime, CITY);
        printTime(rangeAggregationTime, RANGE);
    }

    public Account createRandomAccounts() {
        String[] arrayNames = { "Jordan", "Taylor", "Morgan", "Alex", "Casey" };
        String[] arrayCities = { "Tehran", "Sydney", "London", "Moscow", "Tokyo", "Istanbul", "Berlin", "Madrid",
                "Paris",
                "Rome" };
        int randomName = (int) (Math.random() * arrayNames.length);
        int randomCity = (int) (Math.random() * arrayCities.length);
        double min = 1;
        double max = 10000000;
        double randomBalance = min + (max - min) * Math.random();
        double roundedBalance = Math.round(randomBalance * 100.0) / 100.0;
        Account acc = new Account(arrayNames[randomName], roundedBalance,
                arrayCities[randomCity]);
        return acc;
    }

    public long getTime() {
        return System.nanoTime();
    }

    public long calcTime(long begin, long finish) {
        return (finish - begin) / TEST_NUM;
    }

    public void printTime(long time, String operation) {
        switch (operation) {
            case "add":
                System.out.printf("%-20s %10d accounts in: %10d nanoseconds%n", "Added", TEST_NUM, time);
                break;
            case "find":
                System.out.printf("%-20s %10d accounts in: %10d nanoseconds%n", "Found", TEST_NUM, time);
                break;
            case "city":
                System.out.printf("%-20s %10d accounts in: %10d nanoseconds%n", "City aggregation", TEST_NUM, time);
                break;
            case "range":
                System.out.printf("%-20s %10d accounts in: %10d nanoseconds%n", "Range aggregation", TEST_NUM, time);
                break;
            default:
                System.out.println("Unsupported operation");
                break;
        }
    }

    public void findAccount(Bank bank, int id) {
        Account acc = bank.findAccount(id);
        if (acc != null) {
            acc.print();
        } else {
            System.out.println("Account has not been found");
        }
    }

    public void findAccount(Bank2 bank2, int id) {
        Account acc = bank2.findAccount(id);
        if (acc != null) {
            acc.print();
        } else {
            System.out.println("Account has not been found");
        }
    }

    public void addAccount(Bank bank, Account acc) {
        if (bank.addAccount(acc)) {
            System.out.println("Account has been created successfully");
        } else {
            System.out.println("Account is duplicate and has not been created");
        }
    }

    public void addAccount(Bank2 bank2, Account acc) {
        if (bank2.addAccount(acc)) {
            System.out.println("Account has been created successfully");
        } else {
            System.out.println("Account is duplicate and has not been created");
        }
    }
}
