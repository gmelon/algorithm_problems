import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://www.acmicpc.net/problem/1238
 */

public class Main {

    public static final int INF = 1_000_000_000;

    static class Node implements Comparable<Node> {

        int number;
        int time;

        public Node(int number, int time) {
            this.number = number;
            this.time = time;
        }

        @Override
        public int compareTo(Node o) {
            return this.time - o.time;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 노드 개수
        int M = sc.nextInt(); // 간선 개수
        int X = sc.nextInt(); // 목표 노드

        List<List<Node>> graph = new ArrayList<>();
        List<List<Node>> reverseGraph = new ArrayList<>();
        // 그래프 초기화
        for (int i = 0; i < N + 1; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }

        // 간선 입력
        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int distance = sc.nextInt();
            
            graph.get(from).add(new Node(to, distance));
            reverseGraph.get(to).add(new Node(from, distance));
        }

        sc.close();

        // 풀이 시작

        // 1. X -> 전체 노드 거리 계산
        int[] times = new int[N + 1];
        times = dijkstra(graph, X, N);

        // 2. 전체 노드 -> X 로의 거리 계산 후 (누적)
        int[] tempTimes = dijkstra(reverseGraph, X, N);
        for (int i = 1; i < N + 1; i++) {
            times[i] += tempTimes[i];
        }

        int max = Integer.MIN_VALUE;
        for (int i = 1, timeLength = times.length; i < timeLength; i++) {
            max = Math.max(max, times[i]);
        }

        System.out.println(max);
    }

    public static int[] dijkstra(List<List<Node>> graph, int startNodeNumber, int N) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] times = new int[N + 1];
        Arrays.fill(times, INF);

        // 초기 정점 설정
        times[startNodeNumber] = 0;
        pq.offer(new Node(startNodeNumber, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.time > times[cur.number]) {
                continue;
            }

            for (Node neighbor : graph.get(cur.number)) {
                int newTime = times[cur.number] + neighbor.time;

                if (newTime < times[neighbor.number]) {
                    times[neighbor.number] = newTime;
                    pq.offer(new Node(neighbor.number, newTime));
                }
            }
        }

        return times;
    }

}
