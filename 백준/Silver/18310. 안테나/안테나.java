import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();

        // 풀이 시작
        Arrays.sort(arr);

        if (N % 2 == 1) {
            // 집이 홀수개면 중앙값 반환
            System.out.println(arr[N / 2]);
        } else {
            // 집이 짝수개면 작은 값 출력
            System.out.println(arr[N / 2 - 1]);
        }
    }
}
