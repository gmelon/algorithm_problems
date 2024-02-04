import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[][] arr = new int[N][3];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        br.close();

        int[][] dp = new int[N][3];
        // init
        for (int i = 0; i < 3; i++) {
            dp[0][i] = arr[0][i];
        }

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < 3; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k <= 2; k++) {
                    min = Math.min(min, dp[i - 1][(j + k) % 3]);
                }
                dp[i][j] = min + arr[i][j];
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            answer = Math.min(answer, dp[N - 1][i]);
        }
        System.out.println(answer);
    }

}