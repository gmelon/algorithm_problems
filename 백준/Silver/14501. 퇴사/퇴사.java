import java.util.Scanner;

public class Main {

    static class Counseling {
        int time;
        int price;

        public Counseling(int time, int price) {
            this.time = time;
            this.price = price;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        Counseling[] counselings = new Counseling[N];
        for (int i = 0; i < N; i++) {
            counselings[i] = new Counseling(sc.nextInt(), sc.nextInt());
        }

        sc.close();

        // 풀이 시작
        int[] dp = new int[N + 1];
        for (int i = 0; i < N; i++) {
            int completeDate = i + counselings[i].time;
            if (completeDate <= N) {
                int currentMax = 0;
                for (int j = i; j >= 0; j--) {
                    currentMax = Math.max(currentMax, dp[j]);
                }
                dp[completeDate] = Math.max(dp[completeDate], currentMax + counselings[i].price);
            }
        }

        // 최대값 찾아서 출력
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            answer = Math.max(answer, dp[i]);
        }
        System.out.println(answer);
    }
}
