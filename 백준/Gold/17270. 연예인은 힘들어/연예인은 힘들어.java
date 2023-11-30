import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final int INF = 1_000_000_000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt(); // 정점
        int M = sc.nextInt(); // 간선

        int[][] graph = new int[V + 1][V + 1];

        // 그래프 초기화
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = INF;
                }
            }
        }

        // 간선 입력
        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int cost = sc.nextInt();

            // 동일 간선에 여러 cost가 입력될 수 있음
            if (graph[from][to] > cost) {
                graph[from][to] = cost;
                graph[to][from] = cost;
            }
        }

        int jihunIndex = sc.nextInt();
        int sungHaIndex = sc.nextInt();

        sc.close();

        // 풀이 시작

        // 먼저 모든 정점 간의 거리를 계산한다
        for (int k = 1; k < graph.length; k++) {
            for (int i = 1; i < graph.length; i++) {
                for (int j = 1; j < graph.length; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        // 서로 간의 최단 거리를 계산한다
        int minDistance = Integer.MAX_VALUE;
        for (int k = 1; k < graph.length; k++) {
            if (k == jihunIndex || k == sungHaIndex) {
                continue;
            }

            minDistance = Math.min(minDistance, graph[jihunIndex][k] + graph[k][sungHaIndex]);
        }

        // 지헌 ~ 성하 사이에 하나씩 정점을 두고 최단 거리를 만족하는 정점들을 구한다
        List<Integer> candidates = new ArrayList<>();
        for (int k = 1; k < graph.length; k++) {
            if (graph[jihunIndex][k] + graph[k][sungHaIndex] == minDistance) {
                // 기존 출발점들은 새로운 약속 장소가 될 수 없다
                if (k != jihunIndex && k != sungHaIndex) {
                    candidates.add(k);
                }
            }
        }

        // 새로운 약속 장소 후보지 중 성하가 먼저 도착하는 곳은 거른다
        candidates.removeIf(candidate -> graph[sungHaIndex][candidate] < graph[jihunIndex][candidate]);

        // 남은 장소 중 지헌이에게서 가장 가까운 장소를 고른다
        int minDistanceFromJiHun = Integer.MAX_VALUE;
        for (Integer candidate : candidates) {
            minDistanceFromJiHun = Math.min(minDistanceFromJiHun, graph[jihunIndex][candidate]);
        }
        for (Iterator<Integer> iterator = candidates.iterator(); iterator.hasNext(); ) {
            int candidate = iterator.next();
            if (graph[jihunIndex][candidate] > minDistanceFromJiHun) {
                iterator.remove();
            }
        }

        // 그 중 번호가 가장 작은 정점을 새로운 약속 장소로 고른다
        if (candidates.isEmpty()) {
            System.out.println(-1);
        } else {
            Collections.sort(candidates);
            System.out.println(candidates.get(0));
        }
    }

}
