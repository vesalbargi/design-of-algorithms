package bank;

import java.util.ArrayList;

class Bank3 {
    public String name;
    public MyHashMap<Integer, Account> accounts;

    public Bank3(String name) {
        this.name = name;
        accounts = new MyHashMap<Integer, Account>();
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

    public MyHashMap<String, Double> getTotalBalancePerCity() {
        MyHashMap<String, Double> totalBalances = new MyHashMap<>();
        for (Account acc : accounts.getValues()) {
            Double balance = totalBalances.search(acc.getCity());
            if (balance == null) {
                totalBalances.put(acc.getCity(), acc.getBalance());
            } else {
                totalBalances.put(acc.getCity(), acc.getBalance() + balance);
            }
        }
        return totalBalances;
    }

    public MyHashMap<String, Integer> getTotalCountPerCity() {
        MyHashMap<String, Integer> totalCounts = new MyHashMap<>();
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

    public void reportCity(MyHashMap<String, Double> balances, MyHashMap<String, Integer> counts) {
        System.out.println();
        System.out.println("\n City \t \t Total Balance \t \t Average Balance");
        for (String city : balances.getKeySet()) {
            System.out.println(city + "\t \t " + balances.search(city) + " \t \t "
                    + balances.search(city) / (double) counts.search(city));
        }
    }

    public MyHashMap<Integer, Integer> getTotalCountPerRange(ArrayList<Integer> ranges) {
        MyHashMap<Integer, Integer> totalCountsInRange = new MyHashMap<>();
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
        return totalCountsInRange;
    }

    public void reportRanges(ArrayList<Integer> ranges, MyHashMap<Integer, Integer> countsPerRange) {
        System.out.println();
        for (int i = 0; i < ranges.size() - 1; i++) {
            System.out.println(
                    "Range " + i + ": Number of accounts between " + ranges.get(i) + " and " + ranges.get(i + 1) + " = "
                            + countsPerRange.search(i));
        }
        System.out.println();
    }
}
