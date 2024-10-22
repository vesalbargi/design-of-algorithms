package bank;

import java.util.ArrayList;

/**
 *
 * @author hooman
 */

public class Bank {
    private String name;
    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        accounts = new ArrayList<Account>();
    }

    // find the account for the given id. Returns null if not found
    public Account findAccount(int id) {
        for (Account acc : accounts) {
            if (acc.getID() == id) {
                return acc;
            }
        }
        return null;
    }

    public boolean addAccount(Account account) {
        if (findAccount(account.getID()) == null) {
            accounts.add(account);
            return true;
        } else {
            return false;
        }
    }

    public void printAccounts() {
        for (Account acc : accounts) {
            acc.print();
        }
    }

    public double calcTotalBalance() {
        double total = 0;
        for (Account acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }

    public ArrayList<String> populateDistinctCityList() {
        ArrayList<String> cities = new ArrayList<>();
        for (Account acc : accounts) {
            boolean found = false;
            for (String city : cities) {
                if (acc.getCity().equals(city)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                cities.add(acc.getCity());
            }
        }
        return cities;
    }

    public ArrayList<Double> getTotalBalancePerCity(ArrayList<String> cities) {
        ArrayList<Double> totalBalances = new ArrayList<>();
        for (String city : cities) {
            double totalBalance = 0;
            for (Account acc : accounts) {
                if (acc.getCity().equals(city)) {
                    totalBalance += acc.getBalance();
                }
            }
            totalBalances.add(totalBalance);
        }
        return totalBalances;
    }

    public ArrayList<Integer> getTotalCountPerCity(ArrayList<String> cities) {
        ArrayList<Integer> totalCounts = new ArrayList<>();
        for (String city : cities) {
            int count = 0;
            for (Account acc : accounts) {
                if (acc.getCity().equals(city)) {
                    count++;
                }
            }
            totalCounts.add(count);
        }
        return totalCounts;
    }

    public void reportTotalPerCity(ArrayList<String> cities, ArrayList<Integer> counts, ArrayList<Double> totals) {
        System.out.println("\n City \t \t Total Balance \t \t Average Balance");
        for (int i = 0; i < counts.size(); i++)
            System.out.println(
                    cities.get(i) + " \t \t " + totals.get(i) + " \t \t " + totals.get(i) / (double) counts.get(i));
    }

    public ArrayList<Integer> getTotalCountPerRange(ArrayList<Integer> ranges) {
        ArrayList<Integer> totalCountsInRange = new ArrayList<>();
        for (int i = 0; i < ranges.size() - 1; i++) {
            int max = ranges.get(i + 1);
            int count = 0;
            for (Account acc : accounts) {
                double balance = acc.getBalance();
                if (balance >= ranges.get(i) && balance <= max) {
                    count++;
                }
            }
            totalCountsInRange.add(count);
        }
        return totalCountsInRange;
    }

    public void reportRanges(ArrayList<Integer> ranges, ArrayList<Integer> counts) {
        System.out.println();
        for (int i = 0; i < ranges.size() - 1; i++) {
            System.out.println("Number of accounts between " + ranges.get(i) + " and " + ranges.get(i + 1) + " = "
                    + counts.get(i));
        }
        System.out.println();
    }
}
