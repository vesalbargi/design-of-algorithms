package bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HoomanTestSimple {
    public static void main(String[] args) {
        ArrayList<String> cities;
        Integer[] ranges = { 0, 100, 1000, 5000, 10000, 20000, 25000, 45000, 50000, 80000, 85000, 87000, 88000, 100000,
                110000,
                120000, 122000, 130000, 200000, 250000, 300000, 310000, 400000, 400500, 800000, 850000, 1000000,
                1000900, 2000000, 200000000, 2000000000 };
        ArrayList<Integer> rangesList = new ArrayList<Integer>(Arrays.asList(ranges));

        Account acc1 = new Account(1, "Kim", 100, "Sydney");
        Account acc2 = new Account(2, "Jack", 1800, "Sydney");
        Account acc3 = new Account(3, "Jill", 20000, "Tehran");
        Account acc4 = new Account(4, "Robert", 8000, "Tehran");

        ArrayList<Account> accounts = new ArrayList<>();

        accounts.add(acc1);
        accounts.add(acc2);
        accounts.add(acc3);
        accounts.add(acc4);

        Bank bank = new Bank("Hooman Bank 1");

        for (Account ac : accounts) {
            if (!bank.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        // test duplicate adding. Should not work
        for (Account ac : accounts) {
            if (bank.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        System.out.println("Total balance = " + bank.calcTotalBalance());

        // test search (valid accounts)
        for (int id = 1; id <= 4; id++) {
            Account ac = bank.findAccount(id);
            if (ac == null || !(ac.getName().equals(accounts.get(id - 1).getName()))
                    || ac.getID() != accounts.get(id - 1).getID()
                    || !(ac.getCity().equals(accounts.get(id - 1).getCity()))) {
                System.out.println("error in searching valid accounts");
                System.exit(0);
            }
            if (ac.withdraw(100))
                ac.deposit(100); // don't change the balance
        }

        System.out.println("Total balance after withdraw = " + bank.calcTotalBalance());

        // test search (invalid accounts)
        for (int id = 5; id <= 1000; id++) {
            if (bank.findAccount(id) != null) {
                System.out.println("error in searching invalid accounts");
                System.exit(0);
            }
        }

        // test city aggregation-Balance
        cities = bank.populateDistinctCityList();
        ArrayList<Double> balances = bank.getTotalBalancePerCity(cities);

        // test city aggregation-Count
        ArrayList<Integer> counts = bank.getTotalCountPerCity(cities);

        // test range aggregation-Count
        ArrayList<Integer> countsPerRange = bank.getTotalCountPerRange(rangesList);

        bank.reportTotalPerCity(cities, counts, balances);
        bank.reportRanges(rangesList, countsPerRange);

        // bank2 hashmap
        Bank2 bank2 = new Bank2("Hooman Bank 2");

        for (Account ac : accounts) {
            if (!bank2.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        // test duplicate adding. Should not work
        for (Account ac : accounts) {
            if (bank2.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        System.out.println("\n\nTotal balance = " + bank2.calcTotalBalance());

        // test search (valid accounts)
        for (int id = 1; id <= 4; id++) {
            Account ac = bank2.findAccount(id);
            if (ac == null || !(ac.getName().equals(accounts.get(id - 1).getName()))
                    || ac.getID() != accounts.get(id - 1).getID()
                    || !(ac.getCity().equals(accounts.get(id - 1).getCity()))) {
                System.out.println("error in searching valid accounts");
                System.exit(0);
            }
            if (ac.withdraw(100))
                ac.deposit(100); // don't change the balance
        }

        System.out.println("Total balance after withdraw = " + bank2.calcTotalBalance());

        // test search (invalid accounts)
        for (int id = 5; id <= 1000; id++) {
            if (bank2.findAccount(id) != null) {
                System.out.println("error in searching invalid accounts");
                System.exit(0);
            }
        }

        // test city aggregation-Balance
        HashMap<String, Double> balances1 = bank2.getTotalBalancePerCity();

        // test city aggregation-Count
        HashMap<String, Integer> counts1 = bank2.getTotalCountPerCity();

        // test range aggregation-Count
        ArrayList<Integer> countsPerRange1 = bank2.getTotalCountPerRange(rangesList);

        // test count aggregation
        for (int k = 0; k < counts.size(); k++) {
            if (Integer.compare(counts1.get(cities.get(k)), counts.get(k)) != 0) {
                System.out.println("error in city-count aggregation");
                System.exit(0);
            }
        }

        // test balance aggregation
        for (int k = 0; k < balances.size(); k++) {
            if (Double.compare(balances1.get((cities.get(k))), balances.get(k)) != 0) {
                System.out.println("error in city-balance aggregation");
                System.exit(0);
            }
        }

        // test range aggregation
        for (int k = 0; k < countsPerRange.size(); k++) {
            if (Integer.compare(countsPerRange1.get(k), countsPerRange.get(k)) != 0) {
                System.out.println("error in range-count aggregation");
                System.exit(0);
            }
        }

        bank2.reportCity(balances1, counts1);

        // bank3
        Bank3 bank3 = new Bank3("Hooman Bank 3");

        for (Account ac : accounts) {
            if (!bank3.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        // test duplicate adding. Should not work
        for (Account ac : accounts) {
            if (bank3.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        System.out.println("\n\nTotal balance = " + bank3.calcTotalBalance());

        // test search (valid accounts)
        for (int id = 1; id <= 4; id++) {
            Account ac = bank3.findAccount(id);
            if (ac == null || !(ac.getName().equals(accounts.get(id - 1).getName()))
                    || ac.getID() != accounts.get(id - 1).getID()
                    || !(ac.getCity().equals(accounts.get(id - 1).getCity()))) {
                System.out.println("error in searching valid accounts");
                System.exit(0);
            }
            if (ac.withdraw(100))
                ac.deposit(100); // don't change the balance
        }

        System.out.println("Total balance after withdraw = " + bank3.calcTotalBalance());

        // test search (invalid accounts)
        for (int id = 5; id <= 1000; id++) {
            if (bank3.findAccount(id) != null) {
                System.out.println("error in searching invalid accounts");
                System.exit(0);
            }
        }

        // test city aggregation-Balance
        MyHashMap<String, Double> balances2 = bank3.getTotalBalancePerCity();

        // test city aggregation-Count
        MyHashMap<String, Integer> counts2 = bank3.getTotalCountPerCity();

        // test range aggregation-Count
        ArrayList<Integer> countsPerRange2 = bank3.getTotalCountPerRange(rangesList);

        // test count aggregation
        for (int k = 0; k < counts.size(); k++) {
            if (Integer.compare(counts2.get(cities.get(k)), counts.get(k)) != 0) {
                System.out.println("error in city-count aggregation");
                System.exit(0);
            }
        }

        // test balance aggregation
        for (int k = 0; k < balances.size(); k++) {
            if (Double.compare(balances2.get((cities.get(k))), balances.get(k)) != 0) {
                System.out.println("error in city-balance aggregation");
                System.exit(0);
            }
        }

        // test range aggregation
        for (int k = 0; k < countsPerRange.size(); k++) {
            if (Integer.compare(countsPerRange2.get(k), countsPerRange.get(k)) != 0) {
                System.out.println("error in range-count aggregation");
                System.exit(0);
            }
        }

        bank3.reportCity(cities, balances2, counts2);

        // bank4 BST

        Bank4 bank4 = new Bank4("Hooman Bank 4");

        for (Account ac : accounts) {
            if (!bank4.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        // test duplicate adding. Should not work
        for (Account ac : accounts) {
            if (bank4.addAccount(ac)) {
                System.out.println("error in adding accounts");
                System.exit(0);
            }
        }

        System.out.println("\n\nTotal balance = " + bank4.calcTotalBalance());

        // test search (valid accounts)
        for (int id = 1; id <= 4; id++) {
            Account ac = bank4.findAccount(id);
            if (ac == null || !(ac.getName().equals(accounts.get(id - 1).getName()))
                    || ac.getID() != accounts.get(id - 1).getID()
                    || !(ac.getCity().equals(accounts.get(id - 1).getCity()))) {
                System.out.println("error in searching valid accounts");
                System.exit(0);
            }
            if (ac.withdraw(100))
                ac.deposit(100); // don't change the balance
        }

        System.out.println("Total balance after withdraw = " + bank4.calcTotalBalance());

        // test search (invalid accounts)
        for (int id = 5; id <= 1000; id++) {
            if (bank4.findAccount(id) != null) {
                System.out.println("error in searching invalid accounts");
                System.exit(0);
            }
        }

        // test city aggregation-Balance
        BinarySearchTree<String, Double> balances3 = bank4.getTotalBalancePerCity();

        // test city aggregation-Count
        BinarySearchTree<String, Integer> counts3 = bank4.getTotalCountPerCity();

        // test range aggregation-Count
        ArrayList<Integer> countsPerRange3 = bank4.getTotalCountPerRange(rangesList);

        // test count aggregation
        for (int k = 0; k < counts.size(); k++) {
            if (Integer.compare(counts3.search(cities.get(k)), counts.get(k)) != 0) {
                System.out.println("error in city-count aggregation");
                System.exit(0);
            }
        }

        // test balance aggregation
        for (int k = 0; k < balances.size(); k++) {
            if (Double.compare(balances3.search((cities.get(k))), balances.get(k)) != 0) {
                System.out.println("error in city-balance aggregation");
                System.exit(0);
            }
        }

        // test range aggregation
        for (int k = 0; k < countsPerRange.size(); k++) {
            if (Integer.compare(countsPerRange3.get(k), countsPerRange.get(k)) != 0) {
                System.out.println("error in range-count aggregation");
                System.exit(0);
            }
        }

        bank4.reportCity(cities, balances3, counts3);
    }
}
