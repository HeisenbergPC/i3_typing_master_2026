import java.util.Scanner;

public class TypingMasterMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n------------ Welcome to I3 Typing Master v2026 -----------");
            System.out.println("To begin, please select one of the following options:");
            System.out.println("  1. Login");
            System.out.println("  2. Register");
            System.out.println("  3. Exit");
            System.out.print("Choose an option: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Login selected.");
                    break;
                case 2:
                    System.out.println("Register selected.");
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 3);

        sc.close();
    }
}