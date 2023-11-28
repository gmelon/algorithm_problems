import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Item {

        int weight, value;

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt(); // 버틸 수 있는 무게

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            items.add(new Item(sc.nextInt(), sc.nextInt()));
        }

        sc.close();

        // 풀이 시작
        int[][] dp = new int[N + 1][K + 1]; // [n : 현재 n번째 물건까지 고려][현재 무게 제한]

        Arrays.fill(dp[0], 0);
        for (int i = 0; i <= N; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i <= N; i++) {
            for (int currentK = 1; currentK <= K; currentK++) {
                Item current = items.get(i - 1); // 실제 items 인덱스는 0부터 시작

                // 현재 무게 제한에 현재 물건을 넣을 수 없는 경우
                if (current.weight > currentK) {
                    dp[i][currentK] = dp[i - 1][currentK]; // 현재 물건을 넣지 않는다
                } else {
                    // 현재 물건을 넣을 수 있는 경우
                    dp[i][currentK] = Math.max(dp[i - 1][currentK],
                        dp[i - 1][currentK - current.weight] + current.value);
                }
            }
        }

        System.out.println(dp[N][K]);
    }

}
