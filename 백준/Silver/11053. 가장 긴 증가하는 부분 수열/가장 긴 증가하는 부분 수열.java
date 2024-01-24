import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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

        int[] dp = new int[n];

        // 풀이 시작
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // 기본값 - 자기 자신만큼 증가
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] < arr[i] && dp[j] >= dp[i]) {
                    dp[i] = dp[j] + 1;
                }
            }
        }

        int max = Arrays.stream(dp).max().getAsInt();
        System.out.println(max);
    }
}