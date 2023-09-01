import java.util.Scanner;

public class Main {

    public static final int INF = 1_000_000_000;

    static class Event {
        int from;
        int to;

        public Event(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        int[][] graph = new int[n + 1][n + 1];
        // 그래프 초기화
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                graph[i][j] = INF;
            }
        }
        for (int i = 1; i < n + 1; i++) {
            graph[i][i] = 0;
        }
        // 간선 입력
        for (int i = 0; i < k; i++) {
            graph[sc.nextInt()][sc.nextInt()] = 1;
        }

        // 전후관계를 알아내고 싶은 전후사건들
        int s = sc.nextInt();
        Event[] events = new Event[s];
        for (int i = 0; i < s; i++) {
            events[i] = new Event(sc.nextInt(), sc.nextInt());
        }

        sc.close();

        // 풀이 시작
        // 플로이드-워셜
        for (int K = 1; K < n + 1; K++) {
            for (int A = 1; A < n + 1; A++) {
                for (int B = 1; B < n + 1; B++) {
                    graph[A][B] = Math.min(graph[A][B], graph[A][K] + graph[K][B]);
                }
            }
        }

        for (Event event : events) {
            if (graph[event.from][event.to] != INF) {
                System.out.println(-1);
            } else if (graph[event.to][event.from] != INF) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }

    }
}
