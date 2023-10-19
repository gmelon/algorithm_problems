import static java.lang.Math.max;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt(); // 자두가 떨어지는 초수
        int W = sc.nextInt(); // 자두의 최대 이동 횟수

        int[] trees = new int[T];
        for (int i = 0; i < T; i++) {
            trees[i] = sc.nextInt();
        }
        sc.close();

        // 풀이 시작
        // W는 1가 0번 움직인 것으로 취급. 즉, W + 1 이 최대로 움직인 것
        int[][][] dp = new int[T + 1][W + 2][3]; // [현재초수][현재이동횟수][현재위치]

        for (int currentT = 1; currentT <= T; currentT++) {
            for (int currentW = 1; currentW <= W + 1; currentW++) {
                if (trees[currentT - 1] == 1) {
                    // 자두가 1번 위치에 존재
                    dp[currentT][currentW][1] = max(dp[currentT - 1][currentW][1], dp[currentT - 1][currentW - 1][2]) + 1;
                    dp[currentT][currentW][2] = max(dp[currentT - 1][currentW - 1][1], dp[currentT - 1][currentW][2]);
                } else {
                    if (currentT == 1 && currentW == 1) {
                        continue;
                    }
                    dp[currentT][currentW][2] = max(dp[currentT - 1][currentW][2], dp[currentT - 1][currentW - 1][1]) + 1;
                    dp[currentT][currentW][1] = max(dp[currentT - 1][currentW - 1][2], dp[currentT - 1][currentW][1]);
                }
            }
        }

//        int answer = 0;
//        for (int w = W + 1; w <= W + 1; w++) {
//            answer = max(dp[T][w][1], dp[T][w][2]);
//        }
        System.out.println(max(dp[T][W + 1][1], dp[T][W + 1][2]));
    }

}
