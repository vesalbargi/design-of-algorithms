package bank;

/**
 *
 * @author Hooman
 */

class Account {
    private int id;
    private static int nextId = 1;
    private String name;
    private double balance;
    private String city;

    public Account(int id, String name, double balance, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new ArithmeticException();
        }
    }

    public Account(String name, double balance, String city) {
        this.id = nextId++;
        this.name = name;
        this.city = city;
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new ArithmeticException();
        }
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getBalance() {
        return balance;
    }

    boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    boolean deposit(double amount) {
        if (amount >= 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }

    public void print() {
        // System.out.println ("ID: "+id +" Name: " +name+ " Balance: " + balance+ "
        // City: "+ city); // old way
        System.out.println(this);
    }

    public String toString() {
        return "ID: " + id + " Name: " + name + " Balance: " + balance + " City: " + city;
    }
}