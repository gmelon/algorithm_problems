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
        // 이 시점엔 2/3개로 완성 가능
        Collections.sort(weights);

        for (int firstIndex = 0; firstIndex < weights.size(); firstIndex++) {
            int firstWeight = weights.get(firstIndex);

            // 2개의 수를 통해 만들 수 있는 C 이하의 최대 수를 찾는다
            int secondIndex = binarySearch(0, weights.size() - 1, C - firstWeight);
            if (secondIndex == firstIndex) {
                continue;
            }

            // 2개의 수로 완성 가능
            if (firstWeight + weights.get(secondIndex) == C) {
                System.out.println(1);
                return;
            }

            // 3개의 수로 가능한지 탐색
            // i + 1 ~ max 사이에서만 탐색하면 됨
            // max를 i + 1 까지 감소시키면서 C -  (currentWeight + max) 가 존재하는지 확인
            for (; secondIndex >= firstIndex + 1; secondIndex--) {
                int thirdIndex = binarySearch(firstIndex + 1, secondIndex - 1, C - (firstWeight + weights.get(secondIndex)));
                if (thirdIndex == secondIndex || thirdIndex == firstIndex) {
                    continue;
                }
                if (weights.get(thirdIndex) == C - (firstWeight + weights.get(secondIndex))) {
                    System.out.println(1);
                    return;
                }
            }
        }

        // 여기 도달하면 모든 경우의 수가 불가능
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
