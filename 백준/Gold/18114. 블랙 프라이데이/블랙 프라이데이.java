import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int C;
    static List<Integer> weights = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        C = sc.nextInt();
        for (int i = 0; i < N; i++) {
            // 먼저 입력받으면서 하나의 숫자로 완성 가능한지 확인
            int weight = sc.nextInt();
            weights.add(weight);

            if (weight == C) {
                System.out.println(1);
                return;
            }
        }
        sc.close();

        // 풀이 시작
        Collections.sort(weights);
        int firstIndex = 0;
        int secondIndex = weights.size() - 1;

        while (firstIndex < secondIndex) {
            int firstAndSecondValue = weights.get(firstIndex) + weights.get(secondIndex);
            if (firstAndSecondValue == C) {
                System.out.println(1);
                return;
            }
            if (firstAndSecondValue > C) {
                secondIndex--;
            } else {
                // firstIndex ~ secondIndex 사이에 조건을 만족하는 값이 있는지 찾는다
                int thirdIndex = binarySearch(firstIndex + 1, secondIndex - 1,
                    C - (firstAndSecondValue));
                if (thirdIndex != firstIndex && thirdIndex != secondIndex) {
                    if (firstAndSecondValue + weights.get(thirdIndex) == C) {
                        System.out.println(1);
                        return;
                    }
                }
                firstIndex++;
            }
        }
        System.out.println(0);
    }

    static int binarySearch(int start, int end, int target) {
        int answer = start;
        while (start <= end) {
            int mid = (start + end) / 2;

            if (weights.get(mid) > target) {
                end = mid - 1;
            } else {
                answer = mid;
                start = mid + 1;
            }
        }
        return answer;
    }

}
