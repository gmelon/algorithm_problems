import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static class Problem {
        int deadline;
        int price;

        public Problem(int deadline, int price) {
            this.deadline = deadline;
            this.price = price;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        List<Problem> problems = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            problems.add(new Problem(sc.nextInt(), sc.nextInt()));
        }
        sc.close();

        // 풀이 시작
        problems.sort(Comparator.comparingInt((Problem problem) -> problem.deadline).reversed());

        int problemIndex = 0;
        int answer = 0;
        PriorityQueue<Problem> pq = new PriorityQueue<>(Comparator.comparingInt((Problem problem) -> problem.price).reversed());
        for (int currentDate = problems.get(0).deadline; currentDate > 0; currentDate--) {
            // 현재 날짜인 Problem을 모두 pq에 넣는다
            while (problemIndex < problems.size() && problems.get(problemIndex).deadline == currentDate) {
                pq.offer(problems.get(problemIndex));
                problemIndex++;
            }

            // 오늘까지 남아있는 문제 중 가장 보상이 큰 문제를 해결한다
            if (!pq.isEmpty()) {
                answer += pq.poll().price;
            }
        }

        System.out.println(answer);
    }

}
