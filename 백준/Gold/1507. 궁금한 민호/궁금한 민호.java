import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        int[][] graph = new int[N + 1][N + 1];
        int[][] shortestPath = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                int value = sc.nextInt();
                graph[i][j] = value;
                shortestPath[i][j] = value;
            }
        }
        sc.close();

        // 풀이 시작
        boolean failFlag = false;
        for (int k = 1; k < N + 1; k++) {
            for (int a = 1; a < N + 1; a++) {
                for (int b = 1; b < N + 1; b++) {
                    if (k == a || k == b) {
                        continue;
                    }
                    if (graph[a][k] + graph[k][b] < graph[a][b]) {
                        // 주어진 입력이 두 정점 사이 최단 경로가 아닐 경우 -1 반환
                        failFlag = true;
                        break;
                    }
                    if (graph[a][k] + graph[k][b] == graph[a][b]) {
                        // 기존 간선들의 연결로 최소 거리를 만들 수 있다면,
                        // 바로 연결되는 간선은 불필요
                        shortestPath[a][b] = 0;
                    }
                }
                if (failFlag) {
                    break;
                }
            }
            if (failFlag) {
                break;
            }
        }

        if (failFlag) {
            System.out.println(-1);
        } else {
            int answer = 0;
            for (int i = 1; i < N + 1; i++) {
                for (int j = 1; j < N + 1; j++) {
                    answer += shortestPath[i][j];
                }
            }
            // 거리가 양방향으로 두번 더해지므로 반으로 나눠줌
            System.out.println(answer / 2);
        }
    }

}
