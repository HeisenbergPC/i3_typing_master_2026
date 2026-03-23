import java.util.*;
import java.io.*;

public class Login {

    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        String user = login();
        if (user != null) {
            System.out.println("Redirecting to main menu...");
            // Hook: pass 'user' to main menu
        }
    }

    public static String login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------------- Authentication --------------");

        System.out.print("Username (left blank to cancel): ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Login cancelled.");
            return null;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (authenticate(username, password)) {
            System.out.println("Login successful! Welcome back, " + username + "!");
            return username;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return null;
        }
    }

    private static boolean authenticate(String username, String password) {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            System.out.println("No users registered yet.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2
                        && parts[0].equalsIgnoreCase(username)
                        && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data.");
        }
        return false;
    }
}
