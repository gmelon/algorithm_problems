import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] indegree = new int[N + 1]; // 1번부터 시작
        List<List<Integer>> graph = new ArrayList<>();
        // 그래프 초기화
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            graph.get(from).add(to);
            indegree[to]++;
        }
        sc.close();

        // 풀이 시작
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                // 바로 풀 수 있는 문제를 넣는다
                pq.offer(i);
            }
        }

        List<Integer> answers = new ArrayList<>();
        while (!pq.isEmpty()) {
            // 현재 풀 수 있는 문제중 가장 번호가 작은 문제를 푼다
            Integer current = pq.poll();
            answers.add(current);

            // 현재 풀이한 문제로 인해 변경되는 indegree를 반영한다
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    pq.offer(neighbor);
                }
            }
        }

        String answer = answers.stream()
            .map(String::valueOf)
            .collect(joining(" "));
        System.out.println(answer);
    }

}
