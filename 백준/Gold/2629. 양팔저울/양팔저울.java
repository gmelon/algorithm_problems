import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static boolean[][] dp;
    static int N;
    static List<Integer> chus;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        chus = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            chus.add(sc.nextInt());
        }

        int G = sc.nextInt();
        List<Integer> gusles = new ArrayList<>();
        for (int i = 0; i < G; i++) {
            gusles.add(sc.nextInt());
        }

        sc.close();

        // 풀이 시작
        dp = new boolean[N + 1][40001]; // [추 index][구슬 무게]
        find(0, 0);

        for (int i = 0; i < G; i++) {
            if (dp[N][gusles.get(i)]) {
                System.out.print("Y ");
            } else {
                System.out.print("N ");
            }
        }
    }

    static void find(int index, int weight) {
        if (dp[index][weight]) return;
        dp[index][weight] = true; // index까지 고려헀을 때 weight 만들 수 있음
        if (index == N) return;

        find(index + 1, weight); // 그대로
        find(index + 1, weight + chus.get(index)); // 현재 추 더함
        find(index + 1, Math.abs(weight - chus.get(index))); // 현재 추 빼기
    }

}
