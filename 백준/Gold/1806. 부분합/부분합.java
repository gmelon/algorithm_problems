import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int S = sc.nextInt();
        int[] numbers = new int[N + 1];
        for (int i = 0; i < N; i++) {
            numbers[i] = sc.nextInt();
        }
        sc.close();

        // 풀이 시작
        int start = 0;
        int end = -1;
        int sum = 0;
        int answer = Integer.MAX_VALUE;
        while (start < N && end < N) {
            if (sum < S) {
                end++;
                sum += numbers[end];
            } else {
                answer = Math.min(answer, end - start + 1);
                sum -= numbers[start];
                start++;
            }
        }

        if (answer == Integer.MAX_VALUE) {
            System.out.println(0);
            return;
        }
        System.out.println(answer);
    }

}
