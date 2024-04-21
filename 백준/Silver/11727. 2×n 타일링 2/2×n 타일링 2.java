import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        if (N == 1) {
            System.out.println(1);
            return;
        }

        int[] dp = new int[N + 1];
        dp[1] = 1;
        dp[2] = 3;

        for (int i = 3; i <= N; i++) {
            dp[i] = (dp[i - 2] * 2) % 10_007 + dp[i - 1] % 10_007;
        }

        System.out.println(dp[N] % 10_007);
    }

}