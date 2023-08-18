import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();

        int[][] board = new int[n][m];
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = line.charAt(j) - '0';
            }
        }
        sc.close();

        // 풀이 시작
        // 0번째 행, 열을 0으로 채워 패딩
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = board[i - 1][j - 1];
            }
        }

        int max = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // 현재 좌표가 정사각형에 포함될 수 있을 때만 수행
                if (dp[i][j] == 1) {
                    // 대각선 좌측 상단, 좌측, 상단의 값 중 최소 값에 1을 더해준다
                    int value = dp[i - 1][j];
                    value = Math.min(value, dp[i - 1][j - 1]);
                    value = Math.min(value, dp[i][j - 1]);

                    dp[i][j] = value + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }

        System.out.println((int) Math.pow(max, 2));
    }

}
