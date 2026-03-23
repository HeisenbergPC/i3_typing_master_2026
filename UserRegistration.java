import java.util.Scanner;
import java.util.HashMap;
import java.io.*;
import java.nio.file.*;

/**
 * UserRegistration.java
 * Handles new user registration for I3 Typing Master 2026
 * Saves users to users.txt (format: username:password)
 */
public class UserRegistration {

    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        showRegistrationScreen();
    }

    public static void showRegistrationScreen() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("------------ New User Registration -----------");

            // --- Username ---
            System.out.print("Input a unique username (left blank to cancel): ");
            String username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("[!] Registration cancelled. Returning to main menu...");
                break;
            }

            // Validate username length
            if (username.length() < 3) {
                System.out.println("[!] Username must be at least 3 characters. Try again.");
                continue;
            }

            // Check if username already exists
            if (usernameExists(username)) {
                System.out.println("[!] Username \"" + username + "\" is already taken. Try another.");
                continue;
            }

            // --- Password ---
            System.out.print("Input password: ");
            String password = readPassword(scanner);

            if (password.isEmpty()) {
                System.out.println("[!] Password cannot be empty. Try again.");
                continue;
            }

            if (password.length() < 6) {
                System.out.println("[!] Password must be at least 6 characters. Try again.");
                continue;
            }

            // --- Confirm Password ---
            System.out.print("Input confirm password: ");
            String confirmPassword = readPassword(scanner);

            if (!password.equals(confirmPassword)) {
                System.out.println("[!] Passwords do not match. Try again.");
                continue;
            }

            // --- Save User ---
            if (saveUser(username, password)) {
                System.out.println();
                System.out.println("[✔] Registration successful! Welcome, " + username + "!");
                System.out.println("----------------------------------------------");
            } else {
                System.out.println("[!] An error occurred while saving your account. Please try again.");
            }

            break;
        }

        scanner.close();
    }

    /**
     * Masks password input with asterisks (*).
     * Falls back to plain input if console is unavailable (e.g., IDE).
     */
    private static String readPassword(Scanner scanner) {
        Console console = System.console();
        if (console != null) {
            char[] pwd = console.readPassword();
            return new String(pwd);
        } else {
            // IDE fallback: show plain text with a note
            System.out.print("(hidden input unavailable in IDE — type here): ");
            return scanner.nextLine().trim();
        }
    }

    /**
     * Checks if a username already exists in users.txt
     */
    private static boolean usernameExists(String username) {
        File file = new File(USERS_FILE);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length >= 1 && parts[0].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Error reading user data: " + e.getMessage());
        }

        return false;
    }

    /**
     * Saves a new user to users.txt in format: username:password
     */
    private static boolean saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("[!] Error saving user: " + e.getMessage());
            return false;
        }
    }
}