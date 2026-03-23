import java.util.Scanner;
import java.io.*;
import java.util.*;

/**
 * UserResetPassword.java
 * Handles password reset for I3 Typing Master 2026
 * Reads/writes users from users.txt (format: username:password)
 */
public class UserResetPassword {

    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        showResetPasswordScreen();
    }

    public static void showResetPasswordScreen() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("------------ Reset Password -----------");

            // --- Username ---
            System.out.print("Input your username (left blank to cancel): ");
            String username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("[!] Reset cancelled. Returning to main menu...");
                break;
            }

            // Check if the user exists
            if (!usernameExists(username)) {
                System.out.println("[!] Username \"" + username + "\" not found. Try again.");
                continue;
            }

            // --- Current Password Verification ---
            System.out.print("Input your current password: ");
            String currentPassword = readPassword(scanner);

            if (!verifyPassword(username, currentPassword)) {
                System.out.println("[!] Incorrect current password. Try again.");
                continue;
            }

            // --- New Password ---
            System.out.print("Input new password: ");
            String newPassword = readPassword(scanner);

            if (newPassword.isEmpty()) {
                System.out.println("[!] New password cannot be empty. Try again.");
                continue;
            }

            if (newPassword.length() < 6) {
                System.out.println("[!] New password must be at least 6 characters. Try again.");
                continue;
            }

            if (newPassword.equals(currentPassword)) {
                System.out.println("[!] New password must be different from current password. Try again.");
                continue;
            }

            // --- Confirm New Password ---
            System.out.print("Input confirm new password: ");
            String confirmPassword = readPassword(scanner);

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("[!] Passwords do not match. Try again.");
                continue;
            }

            // --- Update Password ---
            if (updatePassword(username, newPassword)) {
                System.out.println();
                System.out.println("[✔] Password reset successful for user: " + username);
                System.out.println("----------------------------------------------");
            } else {
                System.out.println("[!] An error occurred while updating your password. Please try again.");
            }

            break;
        }

        scanner.close();
    }

    /**
     * Masks password input with asterisks.
     * Falls back to plain input if console is unavailable (e.g., IDE).
     */
    private static String readPassword(Scanner scanner) {
        Console console = System.console();
        if (console != null) {
            char[] pwd = console.readPassword();
            return new String(pwd);
        } else {
            System.out.print("(hidden input unavailable in IDE — type here): ");
            return scanner.nextLine().trim();
        }
    }

    /**
     * Checks if a username exists in users.txt
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
     * Verifies if the entered password matches the stored one
     */
    private static boolean verifyPassword(String username, String password) {
        File file = new File(USERS_FILE);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2
                        && parts[0].equalsIgnoreCase(username)
                        && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Error verifying password: " + e.getMessage());
        }

        return false;
    }

    /**
     * Updates the user's password in users.txt
     */
    private static boolean updatePassword(String username, String newPassword) {
        File file = new File(USERS_FILE);
        if (!file.exists()) return false;

        List<String> lines = new ArrayList<>();
        boolean updated = false;

        // Read all users
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2 && parts[0].equalsIgnoreCase(username)) {
                    lines.add(parts[0] + ":" + newPassword); // replace password
                    updated = true;
                } else {
                    lines.add(line); // keep as-is
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Error reading user data: " + e.getMessage());
            return false;
        }

        if (!updated) return false;

        // Write all users back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("[!] Error writing user data: " + e.getMessage());
            return false;
        }
    }
}