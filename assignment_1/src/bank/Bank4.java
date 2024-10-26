package bank;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Bank4 {
    public String name;
    public BinarySearchTree<Integer, Account> accounts;

    public Bank4(String name) {
        this.name = name;
        accounts = new BinarySearchTree<Integer, Account>();
    }

    public Account findAccount(int id) {
        return accounts.search(id);
    }

    public boolean addAccount(Account account) {
        if (findAccount(account.getID()) == null) {
            accounts.put(account.getID(), account);
            return true;
        } else {
            return false;
        }
    }

    public void printAccounts() {
        for (Account acc : accounts.getValues()) {
            acc.print();
        }
    }

    public double calcTotalBalance() {
        double total = 0;
        for (Account acc : accounts.getValues()) {
            total += acc.getBalance();
        }
        return total;
    }

    public BinarySearchTree<String, Double> getTotalBalancePerCity() {
        BinarySearchTree<String, Double> totalBalances = new BinarySearchTree<>();
        for (Account acc : accounts.getValues()) {
            Double balance = totalBalances.search(acc.getCity());
            if (balance == null) {
                totalBalances.put(acc.getCity(), acc.getBalance());
            } else {
                totalBalances.put(acc.getCity(), balance + acc.getBalance());
            }
        }
        return totalBalances;
    }

    public BinarySearchTree<String, Integer> getTotalCountPerCity() {
        BinarySearchTree<String, Integer> totalCounts = new BinarySearchTree<>();
        for (Account acc : accounts.getValues()) {
            Integer count = totalCounts.search(acc.getCity());
            if (count == null) {
                totalCounts.put(acc.getCity(), 1);
            } else {
                totalCounts.put(acc.getCity(), count + 1);
            }
        }
        return totalCounts;
    }

    public void reportCity(ArrayList<String> cities, BinarySearchTree<String, Double> balances,
            BinarySearchTree<String, Integer> counts) {
        System.out.println("\n City \t \t Total Balance \t \t Average Balance");
        for (String city : cities) {
            System.out.println(city + "\t \t " + balances.search(city) + " \t \t "
                    + balances.search(city) / (double) counts.search(city));
        }
    }

    public ArrayList<Integer> getTotalCountPerRange(ArrayList<Integer> ranges) {
        BinarySearchTree<Integer, Integer> totalCountsInRange = new BinarySearchTree<>();
        for (int i = 0; i < ranges.size() - 1; i++) {
            int max = ranges.get(i + 1);
            int count = 0;
            for (Account acc : accounts.getValues()) {
                double balance = acc.getBalance();
                if (balance >= ranges.get(i) && balance <= max) {
                    count++;
                }
            }
            totalCountsInRange.put(i, count);
        }
        return (ArrayList<Integer>) totalCountsInRange.getValues().stream()
                .collect(Collectors.toList());
    }

    public void reportRanges(ArrayList<Integer> ranges, BinarySearchTree<Integer, Integer> countsPerRange) {
        System.out.println();
        for (int i = 0; i < ranges.size() - 1; i++) {
            System.out.println(
                    "Range " + i + ": Number of accounts between " + ranges.get(i) + " and " + ranges.get(i + 1) + " = "
                            + countsPerRange.search(i));
        }
        System.out.println();
    }
}
