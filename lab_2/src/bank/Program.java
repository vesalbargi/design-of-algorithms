package bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Account acc1 = new Account(1, "Kim", 100, "Sydney");
        Account acc2 = new Account(2, "Jack", 1800, "Sydney");
        Account acc3 = new Account(3, "Jill", 20000, "Tehran");
        Account acc4 = new Account(4, "Robert", 8000, "Tehran");
        Bank bank = new Bank("Hooman Bank");

        // adding accounts
        bank.addAccount(acc1);
        bank.addAccount(acc2);
        bank.addAccount(acc3);
        bank.addAccount(acc4);
        if (bank.addAccount(acc4)) {
            System.out.println("Account has been created successfully");
        } else {
            System.out.println("Account is duplicate and has not been created");
        }

        // or better way
        addAccount(bank, acc4);

        if (acc1.deposit(-60)) {
            System.out.println(" deposite was succesful and the new balance is " + acc1.getBalance());
        } else {
            System.out.println(" deposite was not succesful");
        }

        if (acc2.withdraw(600)) {
            System.out.println(" withdraw was succesful and the new balance is " + acc2.getBalance());
        } else {
            System.out.println(" withdraw was not succesful");
        }

        // or this one which is a better design
        // find an account
        Account acc = bank.findAccount(3);
        withdraw(acc, 50);

        bank.printAccounts();
        double total = bank.calcTotalBalance();
        System.out.println("Total balance = " + total);

        // search accounts
        int id = 1;
        Scanner sc = new Scanner(System.in);
        while (id != 0) {
            System.out.print(" Enter an account ID: ( 0 to exit) ");
            id = sc.nextInt();
            acc = bank.findAccount(id);
            if (acc != null)
                acc.print();
            else
                System.out.println(" Account has not been found");
        }

        // report total balances for all accounts for all given cities
        // bank.reportCity1(); //hardcoded
        // bank.reportRanges1(); //hardcoded

        // Data aggregation

        // Data Aggregation Report: print total and average balance per city
        ArrayList<String> cities = bank.populateDistinctCityList();
        ArrayList<Double> balances = bank.getTotalBalancePerCity(cities);
        ArrayList<Integer> counts = bank.getTotalCountPerCity(cities);
        bank.reportTotalPerCity(cities, counts, balances);

        // Data Aggregation Report: print number of accounts per balance range
        Integer[] r = { 1, 1000, 10000, 100000, 10000000 };
        ArrayList<Integer> ranges = new ArrayList<Integer>(Arrays.asList(r));
        ArrayList<Integer> countsPerRange = bank.getTotalCountPerRange(ranges);
        bank.reportRanges(ranges, countsPerRange);

        Bank2 bank2 = new Bank2("hooman Better Bank");

        // adding accounts
        bank2.addAccount(acc1);
        bank2.addAccount(acc2);
        bank2.addAccount(acc3);
        bank2.addAccount(acc4);

        // find an account
        Account accn = bank2.findAccount(3);
        withdraw(accn, 10);

        // Data aggregation
        HashMap<String, Double> cities2 = bank2.getTotalBalancePerCity();
        HashMap<String, Integer> counts2 = bank2.getTotalCountPerCity();
        bank2.reportCity(cities2, counts2);

        HashMap<Integer, Integer> countsPerRange1 = bank2.getTotalCountPerRange(ranges);
        bank2.reportRanges(ranges, countsPerRange1);

    }

    // UI method
    public static void withdraw(Account acc, double amount) {
        if (acc.withdraw(amount)) {
            System.out.println(" withdraw was succesful and the new balance is " + acc.getBalance());
        } else {
            System.out.println(" withdraw was not succesful");
        }
    }

    // UI method
    public static void addAccount(Bank bank, Account acc) {
        if (bank.addAccount(acc)) {
            System.out.println("Account has been created successfully");
        } else {
            System.out.println("Account is duplicate and has not been created");
        }
    }
}