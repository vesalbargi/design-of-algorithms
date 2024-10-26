package bank;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BankCoordinator {
    private static final int ACC_NUM = 1000;
    private ArrayList<Integer> rangesList;
    private ArrayList<Account> accounts;
    private ArrayList<String> cities;

    public BankCoordinator() {
        this.rangesList = initializeRanges();
        this.accounts = initializeAccounts();
    }

    private long getTime() {
        return System.nanoTime();
    }

    public void experiment(int n, int maxRep) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("bankOutput.txt"));
        Bank bank = new Bank("Hooman Bank 1");
        Bank2 bank2 = new Bank2("Hooman Bank 2");
        Bank3 bank3 = new Bank3("Hooman Bank 3");
        Bank4 bank4 = new Bank4("Hooman Bank 4");
        for (int num = 100; num <= n; num += 50) {
            for (int rep = 0; rep < maxRep; rep++) {
                System.out.println("Testing n= " + num);
                writer.write(num + ",");
                testBank(bank, accounts, writer, num);
                testBank2(bank2, accounts, writer, num);
                testBank3(bank3, accounts, writer, num);
                testBank4(bank4, accounts, writer, num);
                writer.write("\n");
            }
        }
        writer.close();
    }

    public void testBank(Bank bank, ArrayList<Account> accounts, BufferedWriter writer, int num) throws IOException {
        // Add
        long begin = getTime();
        for (Account ac : accounts) {
            bank.addAccount(ac);
        }
        long finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Find
        begin = getTime();
        for (int id = 1; id <= ACC_NUM; id++) {
            bank.findAccount(id);
        }
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        cities = bank.populateDistinctCityList();

        // City-count aggregation
        begin = getTime();
        ArrayList<Integer> counts = bank.getTotalCountPerCity(cities);
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // City-balance aggregation
        begin = getTime();
        ArrayList<Double> balances = bank.getTotalBalancePerCity(cities);
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Range aggregation
        begin = getTime();
        ArrayList<Integer> countsPerRange = bank.getTotalCountPerRange(rangesList);
        finish = getTime();
        writer.write((finish - begin) / num + ",");
    }

    public void testBank2(Bank2 bank2, ArrayList<Account> accounts, BufferedWriter writer, int num) throws IOException {
        // Add
        long begin = getTime();
        for (Account ac : accounts) {
            bank2.addAccount(ac);
        }
        long finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Find
        begin = getTime();
        for (int id = 1; id <= ACC_NUM; id++) {
            bank2.findAccount(id);
        }
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // City-count aggregation
        begin = getTime();
        HashMap<String, Integer> counts = bank2.getTotalCountPerCity();
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // City-balance aggregation
        begin = getTime();
        HashMap<String, Double> balances = bank2.getTotalBalancePerCity();
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Range aggregation
        begin = getTime();
        ArrayList<Integer> countsPerRange = bank2.getTotalCountPerRange(rangesList);
        finish = getTime();
        writer.write((finish - begin) / num + ",");
    }

    public void testBank3(Bank3 bank3, ArrayList<Account> accounts, BufferedWriter writer, int num) throws IOException {
        // Add
        long begin = getTime();
        for (Account ac : accounts) {
            bank3.addAccount(ac);
        }
        long finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Find
        begin = getTime();
        for (int id = 1; id <= ACC_NUM; id++) {
            bank3.findAccount(id);
        }
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // City-count aggregation
        begin = getTime();
        MyHashMap<String, Integer> counts = bank3.getTotalCountPerCity();
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // City-balance aggregation
        begin = getTime();
        MyHashMap<String, Double> balances = bank3.getTotalBalancePerCity();
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Range aggregation
        begin = getTime();
        ArrayList<Integer> countsPerRange = bank3.getTotalCountPerRange(rangesList);
        finish = getTime();
        writer.write((finish - begin) / num + ",");
    }

    public void testBank4(Bank4 bank4, ArrayList<Account> accounts, BufferedWriter writer, int num) throws IOException {
        // Add
        long begin = getTime();
        for (Account ac : accounts) {
            bank4.addAccount(ac);
        }
        long finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Find
        begin = getTime();
        for (int id = 1; id <= ACC_NUM; id++) {
            bank4.findAccount(id);
        }
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // City-count aggregation
        begin = getTime();
        BinarySearchTree<String, Integer> counts = bank4.getTotalCountPerCity();
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // City-balance aggregation
        begin = getTime();
        BinarySearchTree<String, Double> balances = bank4.getTotalBalancePerCity();
        finish = getTime();
        writer.write((finish - begin) / num + ",");

        // Range aggregation
        begin = getTime();
        ArrayList<Integer> countsPerRange = bank4.getTotalCountPerRange(rangesList);
        finish = getTime();
        writer.write((finish - begin) / num + ",");
    }

    private ArrayList<Integer> initializeRanges() {
        Integer[] ranges = { 0, 100, 1000, 5000, 10000, 20000, 25000, 45000, 50000, 80000, 85000, 87000, 88000, 100000,
                110000, 120000, 122000, 130000, 200000, 250000, 300000, 310000, 400000, 400500, 800000, 850000, 1000000,
                1000900, 2000000, 200000000, 2000000000 };
        return new ArrayList<Integer>(Arrays.asList(ranges));
    }

    public ArrayList<Account> initializeAccounts() {
        accounts = new ArrayList<>();
        for (int i = 0; i < ACC_NUM; i++) {
            accounts.add(randomAccount());
        }
        return accounts;
    }

    public Account randomAccount() {
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
        Account account = new Account(arrayNames[randomName], roundedBalance,
                arrayCities[randomCity]);
        return account;
    }
}
