import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 현재 휴게소 개수
        int M = sc.nextInt(); // 더 지으려고 하는 휴게소 개수
        int L = sc.nextInt(); // 고속도로의 길이

        List<Integer> current = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            current.add(sc.nextInt());
        }
        sc.close();

        // 풀이 시작
        current.add(0);
        current.add(L);
        current.sort(Comparator.naturalOrder());

        // 휴게소가 없는 구간의 범위
        int start = 1;
        int end = L - 1;
        int answer = 0;
        while (start <= end) {
            int mid = (start + end) / 2;

            // 현재의 mid로 M개의 휴게소를 더 지을 수 있는지 판단
            int count = 0;
            for (int i = 0; i < current.size() - 1; i++) {
                int c = (current.get(i + 1) - current.get(i) - 1) / mid;
                count += c;
            }

            if (count <= M) {
                // 설치 불가, 간격 줄이기
                answer = mid;
                end = mid - 1;
            } else {
                // 현재의 거리로 설치 가능
                // 간격 늘리기
                start = mid + 1;
            }
        }
        System.out.println(answer);
    }

}
