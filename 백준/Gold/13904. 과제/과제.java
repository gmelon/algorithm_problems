import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static class Assignment {

        int day;
        int point;

        public Assignment(int day, int point) {
            this.day = day;
            this.point = point;
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        List<Assignment> assignments = new ArrayList<>();
        int maxDay = 0;
        for (int i = 0; i < N; i++) {
            int day = sc.nextInt();
            int point = sc.nextInt();
            assignments.add(new Assignment(day, point));
            maxDay = Math.max(maxDay, day);
        }
        sc.close();

        // 풀이 시작
        Map<Integer, List<Assignment>> assignmentByDay = assignments.stream()
            .collect(groupingBy(a -> a.day));

        int answer = 0;
        PriorityQueue<Assignment> pq = new PriorityQueue<>(Comparator.comparingInt((Assignment a) -> a.point).reversed());
        for (int i = maxDay; i > 0 ; i--) {
            // 현재 날짜에 가능한 숙제 pq에 넣기
            for (Assignment assignment : assignmentByDay.getOrDefault(i, List.of())) {
                pq.offer(assignment);
            }

            // 현재 가능한 숙제 중 가장 point가 높은 숙제 하나 처리
            if (!pq.isEmpty()) {
                answer += pq.poll().point;
            }
        }

        System.out.println(answer);
    }

}
