import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    static StringBuilder sb = new StringBuilder();
    static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        if (N <= 20) {
            move(1, 3, N); // 모든 블럭 (N개) 을 1에서 3으로 옮기고 싶다
            System.out.println(count);
            System.out.println(sb);
        } else {
            System.out.println(BigDecimal.valueOf(2).pow(N).subtract(BigDecimal.ONE));
        }
    }

    static void move(int from, int to, int size) {
        // N번째 원판 위의 N - 1개를 먼저 빈 칸으로 옮긴다
        if (size > 1) {
            move(from, 6 - (from + to), size - 1);
        }

        // 현재 원판을 to로 옮긴다
        count++;
        sb.append(from).append(" ").append(to).append("\n");

        // 옮겨 두었던 N - 1개의 원판을 to로 다시 옮긴다
        if (size > 1) {
            move(6 - (from + to), to, size - 1);
        }
    }

}