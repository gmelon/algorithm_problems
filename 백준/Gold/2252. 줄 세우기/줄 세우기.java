import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] indegree = new int[N + 1];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < N + 1; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();

            indegree[to]++;
            graph.get(from).add(to);
        }

        sc.close();

        // 풀이 시작
        List<Integer> sortedResult = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        // 초기에 진입 차수가 0인 노드들 넣기
        for (int i = 1; i < indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedResult.add(current);

            // 이웃 노드들의 진입 차수 줄이기
            for (int neighbor : graph.get(current)) {
                indegree[neighbor]--;

                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        System.out.println(sortedResult.stream().map(Object::toString).collect(Collectors.joining(" ")));
    }

}
