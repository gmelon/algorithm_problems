import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 정점 개수
        int M = sc.nextInt(); // 간선 개수

        // 그래프 초기화 (1번 vertex부터 사용)
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 그래프에 간선 정보 추가 (양방향, 길이 1)
        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();

            graph.get(from).add(to);
            graph.get(to).add(from);
        }

        int S = sc.nextInt(); // 출발 지점
        int E = sc.nextInt(); // 도착 지점

        sc.close();

        solution(N, M, graph, S, E);
    }

    public static void solution(int N, int M, List<List<Integer>> graph, int S, int E) {
        int answer = 0;

        // graph 간선 오름차순 정렬 -> 먼저 찾은 경로가 오름차순 경로
        for (List<Integer> g : graph) {
            Collections.sort(g);
        }

        boolean[] visited = new boolean[N + 1];
        List<Integer> sToEPath = bfs(graph, visited, S, E);

        // S -> E에서 찾은 경로 길이 더해주기
        answer += (sToEPath.size() - 1);


        // S -> E에서 찾은 경로를 visited에 true 처리해주고 E -> S 경로 찾기
        visited = new boolean[N + 1];
        for (Integer integer : sToEPath) {
            if (integer != S) {
                visited[integer] = true;
            }
        }

        // 동일하게 오름차순된 graph에서 찾으면 오름차순이 보장됨
        List<Integer> eToSPath = bfs(graph, visited, E, S);

        // E -> S 경로 길이 더해주기
        answer += (eToSPath.size() - 1);

        System.out.println(answer);
    }

    public static List<Integer> bfs(List<List<Integer>> graph, boolean[] visited, int start,
        int target) {
        Queue<List<Integer>> queue = new LinkedList<>();

        List<Integer> init = new ArrayList<>();
        init.add(start);

        queue.offer(init);
        visited[start] = true;

        while (!queue.isEmpty()) {
            List<Integer> current = queue.poll();

            for (Integer neighbors : graph.get(current.get(current.size() - 1))) {
                if (!visited[neighbors]) {
                    visited[neighbors] = true;

                    List<Integer> next = new ArrayList<>(current);
                    next.add(neighbors);

                    if (neighbors == target) {
                        return next;
                    }

                    queue.offer(next);
                }
            }
        }
        return List.of();
    }


}
