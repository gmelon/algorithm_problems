import java.util.Scanner;

public class Main {

    public static final int INF = 1_000_000_000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] graph = new int[n + 1][n + 1];
        int[][] route = new int[n + 1][n + 1];

        // 그래프 초기화
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                graph[i][j] = INF;
                route[i][j] = INF;
            }
        }
        for (int i = 1; i < n + 1; i++) {
            graph[i][i] = 0;
//            route[i][i] = 0;
        }

        // 간선 입력
        for (int i = 0; i < m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int time = sc.nextInt();
            graph[from][to] = time;
            graph[to][from] = time;
            route[from][to] = to;
            route[to][from] = from;
        }

        sc.close();

        // 풀이 시작
        // 플로이드-워셜
        for (int k = 1; k < n + 1; k++) {
            for (int a = 1; a < n + 1; a++) {
                for (int b = 1; b < n + 1; b++) {
                    if (graph[a][b] > graph[a][k] + graph[k][b]) {
                        graph[a][b] = graph[a][k] + graph[k][b];
                        route[a][b] = route[a][k];
                    }
                }
            }
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (route[i][j] == 0 || route[i][j] == INF) {
                    System.out.print("-");
                } else {
                    System.out.print(route[i][j]);
                }

                // 개행
                if (j < n) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }
        }

    }

}
