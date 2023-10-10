import java.util.Scanner;

public class Main {

    public static int answer = 0;
    public static int k, n;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        k = sc.nextInt();
        n = sc.nextInt();
        sc.close();

        // 풀이 시작
        long[][] dp = new long[n + 1][k + n + 1]; // 행 - n, 열 - k
        // 초기값 설정
        dp[0][k] = 1L;

        // dp 테이블 채우기
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < k + n; j++) {
                dp[i + 1][j - 1] += dp[i][j];
                dp[i + 1][j + 1] += dp[i][j];
            }
        }

        // 마지막 행 더하기
        long answer = 0;
        for (int i = 1; i < dp[0].length; i++) {
            answer += dp[n][i];
        }

        System.out.println(answer);
    }

}
