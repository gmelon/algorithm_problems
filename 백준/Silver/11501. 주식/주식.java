import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalN = sc.nextInt();

        for (int i = 0; i < totalN; i++) {
            int N = sc.nextInt(); // 날의 수

            int[] arr = new int[N];
            for (int j = 0; j < arr.length; j++) {
                arr[j] = sc.nextInt();
            }
            solution(N, arr);
        }
        sc.close();
    }

    public static void solution(final int days, final int[] prices) {

        /**
         * [가능한 선택지]
         *
         * 주식 하나를 산다.
         * 원하는 만큼 가지고 있는 주식을 판다.
         * 아무것도 안한다.
         */

        // 다음에 더 큰 값이 있으면 -> 산다
        // 현재가 남은 수 중에 가장 큰 값이면 -> 전부 판다
        // 앞으로 더 큰 값이 없으면 (작거나 같으면) -> 사지 않는다 (아무것도 안 한다)

        // 이를 뒤집으면,

        // 가장 마지막 원소부터 현재 값을 기록해두고
        // 역순으로 탐색하며 만나는 값들이 기록된 값보다 작으면 이익 += 현재 값
        // 크면 새로운 값 갱신
        // 같으면 아무것도 하지 않음

        long count = 0;
        int prevMax = Integer.MIN_VALUE;

        for (int i = prices.length - 1; i >= 0; i--) {
            int current = prices[i];
            if (current > prevMax) {
                prevMax = current;
                continue;
            }

            if (current < prevMax) {
                count += (prevMax - current);
            }
        }

        System.out.println(count);
    }

}
