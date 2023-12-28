import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 땅의 범위
        int A = sc.nextInt(); // 오리 A의 초기 위치
        int B = sc.nextInt(); // 오리 B의 초기 위치

        sc.close();

        // 풀이 시작
        boolean[][] dpA = new boolean[21][N + 1];
        boolean[][] dpB = new boolean[21][N + 1];

        // 초기 위치 지정
        dpA[0][A] = true;
        dpB[0][B] = true;

        for (int dayCount = 0; dayCount < 20; dayCount++) {
            int amount = (int) Math.pow(2, dayCount);
            // 먼저 A 갱신
            for (int i = 1; i < N + 1; i++) {
                if (dpA[dayCount][i]) {
                    if (i + amount <= N) {
                        dpA[dayCount + 1][i + amount] = true;
                    }
                    if (i - amount >= 1) {
                        dpA[dayCount + 1][i - amount] = true;
                    }
                }
            }

            // B 채우면서 A와 비교
            for (int i = 1 ; i < N + 1 ; i++) {
                if (dpB[dayCount][i]) {
                    if (i + amount <= N) {
                        dpB[dayCount + 1][i + amount] = true;
                        if (dpA[dayCount + 1][i + amount]) {
                            System.out.println(dayCount + 1);
                            return;
                        }
                    }
                    if (i - amount >= 1) {
                        dpB[dayCount + 1][i - amount] = true;
                        if (dpA[dayCount + 1][i - amount]) {
                            System.out.println(dayCount + 1);
                            return;
                        }
                    }
                }
            }
        }

        // 여기에 도달하면 만날 수 없음
        System.out.println(-1);
    }

}
