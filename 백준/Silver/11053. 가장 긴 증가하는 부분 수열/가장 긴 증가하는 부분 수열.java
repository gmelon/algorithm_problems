import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // 풀이 시작
        int[] dp = new int[n];
        int max = 0;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            int innerMax = 0;
            for (int j = i + 1; j < n ; j++) {
                if (arr[j] > arr[i]) {
                    innerMax = Math.max(innerMax, dp[j]);
                }
            }
            dp[i] += innerMax;
            max = Math.max(max, dp[i]);
        }

        System.out.println(max);
    }
}

