package bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RandomTest {
    private static final int TEST_NUM = 1000;
    private static final String ADD = "add";
    private static final String FIND = "find";
    private static final String CITY = "city";
    private static final String RANGE = "range";

    public static void main(String[] args) {
        Bank bank = new Bank("Test Bank");
        Bank2 bank2 = new Bank2("Test Bank2");
        RandomTest randomTest = new RandomTest();
        randomTest.testBank(bank);
        randomTest.testBank2(bank2);
    }

    public void testBank(Bank bank) {
        long begin = getTime();
        for (int i = 0; i < TEST_NUM; i++) {
            Account acc = createRandomAccounts();
            bank.addAccount(acc);
            // addAccount(bank, acc);
        }
        long finish = getTime();
        long addAccountTime = calcTime(begin, finish);

        begin = getTime();
        for (int i = 1; i <= TEST_NUM; i++) {
            bank.findAccount(i);
            // findAccount(bank, i);
        }
        finish = getTime();
        long findAccountTime = calcTime(begin, finish);

        begin = getTime();
        ArrayList<String> cities = bank.populateDistinctCityList();
        ArrayList<Double> balances = bank.getTotalBalancePerCity(cities);
        ArrayList<Integer> counts = bank.getTotalCountPerCity(cities);
        // bank.reportTotalPerCity(cities, counts, balances);
        finish = getTime();
        long cityAggregationTime = calcTime(begin, finish);

        begin = getTime();
        Integer[] r = { 1, 1000, 10000, 100000, 10000000 };
        ArrayList<Integer> ranges = new ArrayList<Integer>(Arrays.asList(r));
        ArrayList<Integer> countsPerRange = bank.getTotalCountPerRange(ranges);
        // bank.reportRanges(ranges, countsPerRange);
        finish = getTime();
        long rangeAggregationTime = calcTime(begin, finish);

        System.out.println("-----------------Bank 1-----------------");
        printTime(addAccountTime, ADD);
        printTime(findAccountTime, FIND);
        printTime(cityAggregationTime, CITY);
        printTime(rangeAggregationTime, RANGE);
    }

    public void testBank2(Bank2 bank2) {
        long begin = getTime();
        for (int i = 0; i < TEST_NUM; i++) {
            Account acc = createRandomAccounts();
            bank2.addAccount(acc);
            // addAccount(bank2, acc);
        }
        long finish = getTime();
        long addAccountTime = calcTime(begin, finish);

        begin = getTime();
        for (int i = 1; i <= TEST_NUM; i++) {
            bank2.findAccount(i);
            // findAccount(bank2, i);
        }
        finish = getTime();
        long findAccountTime = calcTime(begin, finish);

        begin = getTime();
        HashMap<String, Double> cities2 = bank2.getTotalBalancePerCity();
        HashMap<String, Integer> counts2 = bank2.getTotalCountPerCity();
        // bank2.reportCity(cities2, counts2);
        finish = getTime();
        long cityAggregationTime = calcTime(begin, finish);

        begin = getTime();
        Integer[] r = { 1, 1000, 10000, 100000, 10000000 };
        ArrayList<Integer> ranges = new ArrayList<Integer>(Arrays.asList(r));
        HashMap<Integer, Integer> countsPerRange1 = bank2.getTotalCountPerRange(ranges);
        // bank2.reportRanges(ranges, countsPerRange1);
        finish = getTime();
        long rangeAggregationTime = calcTime(begin, finish);

        System.out.println("-----------------Bank 2-----------------");
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
                System.out.println("Added " + TEST_NUM + " accounts in " + time + " nanoseconds");
                break;
            case "find":
                System.out.println("Found " + TEST_NUM + " accounts in " + time + " nanoseconds");
                break;
            case "city":
                System.out.println(TEST_NUM + " accounts city aggregation completed in: " + time + " nanoseconds");
                break;
            case "range":
                System.out.println(TEST_NUM + " accounts range aggregation completed in: " + time + " nanoseconds");
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
