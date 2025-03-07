import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private TestRunner testRunner = new TestRunner();

    public void mainMenu(Scanner sc) throws Exception {
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
        while (true) {
            System.out.println("\nChoose a problem:");
            System.out.println("[1] Assignment: Branch and Bound");
            System.out.println("[2] Job Scheduling: Branch and Bound");
            System.out.println("[3] Back to Main Menu");
            int problemChoice = getValidIntInput(sc);
            switch (problemChoice) {
                case 1:
                    testRunner.runAssignmentBB();
                    break;
                case 2:
                    testRunner.runJSBB();
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

    private void standardMenu(Scanner sc) throws Exception {
        while (true) {
            System.out.println("\nChoose a problem:");
            System.out.println("[1] Assignment: Backtracking");
            System.out.println("[2] Knapsack: Branch and Bound");
            System.out.println("[3] Back to Main Menu");
            int problemChoice = getValidIntInput(sc);
            sc.nextLine();
            switch (problemChoice) {
                case 1:
                    testRunner.runAssignmentBT();
                    break;
                case 2:
                    testRunner.runKnapsackBB();
                    runCoordinatorPrompt(sc, testRunner, "Knapsack");
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

    private void coreMenu(Scanner sc) throws Exception {
        while (true) {
            System.out.println("\nChoose a problem:");
            System.out.println("[1] TSP: Dynamic Programming");
            System.out.println("[2] Job Scheduling: Backtracking");
            System.out.println("[3] Back to Main Menu");
            int problemChoice = getValidIntInput(sc);
            sc.nextLine();
            switch (problemChoice) {
                case 1:
                    testRunner.runTSPDP();
                    break;
                case 2:
                    testRunner.runJSBT();
                    runCoordinatorPrompt(sc, testRunner, "JS");
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

    private void runCoordinatorPrompt(Scanner sc, TestRunner testRunner, String algorithm) throws Exception {
        System.out.print("\nWould you like to run the coordinator? (yes/no): ");
        String response = sc.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            testRunner.runCoordinator(algorithm);
        } else if (response.equals("no")) {
            System.out.println("Coordinator execution cancelled.");
        } else {
            System.out.println("Invalid input. Please answer with 'yes' or 'no'.");
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
