import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    public void mainMenu(Scanner sc) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("[1] Core");
            System.out.println("[2] Standard");
            System.out.println("[3] Advanced");
            System.out.println("[4] Exit");
            int levelChoice = getValidIntInput(sc);
            switch (levelChoice) {
                case 1:
                    coreMenu(sc);
                    break;
                case 2:
                    standardMenu(sc);
                    break;
                case 3:
                    advancedMenu(sc);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void advancedMenu(Scanner sc) {

    }

    private void standardMenu(Scanner sc) {
    }

    private void coreMenu(Scanner sc) {
        TestRunner testRunner = new TestRunner();
        while (true) {
            System.out.println("\nChoose a problem:");
            System.out.println("[1] TSP: Dynamic Programming");
            System.out.println("[2] Job Scheduling: Backtracking");
            System.out.println("[3] Back to Main Menu");
            int problemChoice = getValidIntInput(sc);
            switch (problemChoice) {
                case 1:
                    testRunner.testTSPDP();
                    break;
                case 2:
                    testRunner.testJSBT();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private int getValidIntInput(Scanner sc) {
        while (true) {
            try {
                int choice = sc.nextInt();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }
    }
}
