//Task 6
import java.util.*;

public class TypingTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String text = "Java is a powerful programming language";

        System.out.println("--- Typing Test ---");
        System.out.println("Type this sentence:");
        System.out.println(text);

        System.out.print("\nYour input: ");
        String input = sc.nextLine();

        if (input.equals(text)) {
            System.out.println("Perfect typing!");
        } else {
            System.out.println("There are mistakes.");
        }
    }
}