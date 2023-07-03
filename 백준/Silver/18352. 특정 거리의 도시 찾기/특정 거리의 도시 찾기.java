import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 노드 개수
        int M = sc.nextInt(); // 간선 개수
        int K = sc.nextInt(); // 타겟 최단 거리
        int X = sc.nextInt(); // 탐색 시작 노드

        List<List<Integer>> graph = new ArrayList<>();
        // 그래프 초기화 (index 1번부터 사용)
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 간선 정보 입력 받기
        for (int i = 0; i < M; i++) {
            graph.get(sc.nextInt()).add(sc.nextInt());
        }

        solution(graph, X, K);
    }

    public static void solution(List<List<Integer>> graph, final int start, final int targetDistance) {

        boolean[] visited = new boolean[graph.size()];
        int[] distance = new int[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        // 초기 노드 설정
        queue.offer(start);
        visited[start] = true;
        distance[start] = 0;

        while (!queue.isEmpty()) {
            Integer currentNode = queue.poll();
            if (distance[currentNode] >= targetDistance) {
                break;
            }

            // 인접 노드 방문
            for (Integer neighbor : graph.get(currentNode)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                    distance[neighbor] = distance[currentNode] + 1;
                }
            }
        }

        boolean hasAnswer = false;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] == targetDistance) {
                hasAnswer = true;
                System.out.println(i);
            }
        }
        if (!hasAnswer) {
            System.out.println(-1);
        }
    }

}
