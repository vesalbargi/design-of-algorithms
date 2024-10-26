package bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 *
 * @author Hooman
 */

public class Bank2 {
    public String name;
    public HashMap<Integer, Account> accounts;

    public Bank2(String name) {
        this.name = name;
        accounts = new HashMap<Integer, Account>();
    }

    // find the account for the given id. Returns null if not found
    public Account findAccount(int id) {
        return accounts.get(id);
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
        for (Account acc : accounts.values()) {
            acc.print();
        }
    }

    public double calcTotalBalance() {
        double total = 0;
        for (Account acc : accounts.values()) {
            total += acc.getBalance();
        }
        return total;
    }

    public HashMap<String, Double> getTotalBalancePerCity() {
        HashMap<String, Double> totalBalances = new HashMap<>();
        for (Account acc : accounts.values()) {
            Double balance = totalBalances.get(acc.getCity());
            if (balance == null) {
                totalBalances.put(acc.getCity(), acc.getBalance());
            } else {
                totalBalances.put(acc.getCity(), acc.getBalance() + balance);
            }
        }
        return totalBalances;
    }

    public HashMap<String, Integer> getTotalCountPerCity() {
        HashMap<String, Integer> totalcounts = new HashMap<>();
        for (Account acc : accounts.values()) {
            Integer count = totalcounts.get(acc.getCity());
            if (count == null) {
                totalcounts.put(acc.getCity(), 1);
            } else {
                totalcounts.put(acc.getCity(), count + 1);
            }
        }
        return totalcounts;
    }

    public void reportCity(HashMap<String, Double> balances, HashMap<String, Integer> counts) {
        System.out.println();
        System.out.println("\n City \t \t Total Balance \t \t Average Balance");
        for (String city : balances.keySet()) {
            System.out.println(
                    city + "\t \t " + balances.get(city) + " \t \t " + balances.get(city) / (double) counts.get(city));
        }
    }

    public ArrayList<Integer> getTotalCountPerRange(ArrayList<Integer> ranges) {
        HashMap<Integer, Integer> totalCountsInRange = new HashMap<>();
        for (int i = 0; i < ranges.size() - 1; i++) {
            int max = ranges.get(i + 1);
            int count = 0;
            for (Account acc : accounts.values()) {
                double balance = acc.getBalance();
                if (balance >= ranges.get(i) && balance <= max) {
                    count++;
                }
            }
            totalCountsInRange.put(i, count);
        }
        return (ArrayList<Integer>) totalCountsInRange.values().stream()
                .collect(Collectors.toList());
    }

    public void reportRanges(ArrayList<Integer> ranges, HashMap<Integer, Integer> countsPerRange) {
        System.out.println();
        for (int i = 0; i < ranges.size() - 1; i++) {
            System.out.println(
                    "Range " + i + ": Number of accounts between " + ranges.get(i) + " and " + ranges.get(i + 1) + " = "
                            + countsPerRange.get(i));
        }
        System.out.println();
    }
}
