import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 프로세스 개수
        int M = sc.nextInt(); // 확보해야 하는 메모리
        List<Integer> memories = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            memories.add(sc.nextInt());
        }
        for (int i = 0; i < N; i++) {
            costs.add(sc.nextInt());
        }
        sc.close();

        // 풀이 시작
        int[] dp = new int[100 * 100 + 1]; // dp[c] : c 이상의 비용으로 만들 수 있는 메모리의 최대값

        for (int i = 0; i < N; i++) {
            for (int j = dp.length - 1; j >= costs.get(i); j--) {
                dp[j] = Math.max(dp[j], dp[j - costs.get(i)] + memories.get(i));
            }
        }

        for (int i = 0; i < dp.length; i++) {
            if (dp[i] >= M) {
                System.out.println(i);
                return;
            }
        }
    }
}
