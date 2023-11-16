import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int d = sc.nextInt();
        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Integer> stones = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            stones.add(sc.nextInt());
        }
        sc.close();

        // 풀이 시작
        Collections.sort(stones);

        int answer = 0;
        int left = 0;
        int right = d;
        while (left <= right) {
            int mid = (left + right) / 2; // 현재 점프 거리
            int currentPosition = 0;
            int removedStoneCount = 0;
            for (int stone : stones) {
                if (stone - currentPosition < mid) {
                    removedStoneCount++;
                } else {
                    currentPosition = stone;
                }
            }
            if (removedStoneCount <= m) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(answer);
    }

}
