import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 토핑 종류 수

        int A = sc.nextInt(); // 도우의 가격
        int B = sc.nextInt(); // 토핑의 가격

        int C = sc.nextInt(); // 도우의 칼로리

        int[] topingC = new int[N]; // 토핑의 칼로리
        for (int i = 0; i < N; i++) {
            topingC[i] = sc.nextInt();
        }

        System.out.println(solution(A, B, C, topingC));
    }

    public static int solution(int doughPrice, int topingPrice, int doughCalorie, int[] topingCalories) {
        // 같은 토핑 2개 이상 금지
        // 토핑이 없는 피자도 주문 가능

        // 1달러 당 열량이 가장 높은 피자를 구매해야 함
        // "1달러 당 열량"을 반환

        // 토핑의 가격은 모두 같음 -> 따라서 1달러 당 열량이 줄어들 때 까지 칼로리가 높은 토핑부터 넣기

        int[] sortedTopingCalories = Arrays.stream(topingCalories)
            .boxed()
            .sorted(Collections.reverseOrder())
            .mapToInt(Integer::intValue)
            .toArray();

        int totalPrice = doughPrice;
        int currentCalorie = doughCalorie;

        for (int currentTopingCalorie : sortedTopingCalories) {
            // 토핑을 더한 피자의 1달러 당 열량이 더 높다면, 토핑을 올리기
            if (Double.compare((double) (currentCalorie + currentTopingCalorie) / (totalPrice + topingPrice),
                (double) currentCalorie / totalPrice) >= 1) {
                totalPrice += topingPrice;
                currentCalorie += currentTopingCalorie;
            } else {
                return currentCalorie / totalPrice;
            }
        }
        return currentCalorie / totalPrice;
    }

}
