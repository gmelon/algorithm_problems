import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int count;
        try (Scanner scanner = new Scanner(System.in)) {
             count = scanner.nextInt();
        }

        List<String> asterisks = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            asterisks.add("*");
            System.out.println(" ".repeat(count - i) + String.join(" ", asterisks));
        }

    }
}