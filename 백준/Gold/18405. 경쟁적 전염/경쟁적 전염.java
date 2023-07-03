import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static class Virus implements Comparable<Virus> {
        int x, y;
        int type;

        public Virus(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        @Override
        // 대소 비교는 type만 사용, 오름차순
        public int compareTo(Virus o) {
            return this.type - o.type;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 격자 크기
        int K = sc.nextInt(); // 바이러스의 종류

        int[][] board = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                board[i][j] = sc.nextInt();
            }
        }

        int S = sc.nextInt();
        int X = sc.nextInt();
        int Y = sc.nextInt();

        System.out.println(solution(N, K, board, S, new Virus(X, Y, 0)));
    }

    public static int solution(int N, int K, int[][] board, int S, Virus target) {
        PriorityQueue<Virus> pq = new PriorityQueue<>();

        // 먼저 전체를 순회하며 pq에 기존 바이러스들을 넣음
        for (int i = 1; i <= N ; i++) {
            for (int j = 1; j <= N; j++) {
                if (board[i][j] != 0) {
                    pq.offer(new Virus(i, j, board[i][j]));
                }
            }
        }

        // 이제부터 작은 숫자의 바이러스부터 bfs를 수행함
        int currentTime = 0;
        while (currentTime < S) {
            currentTime++;

            PriorityQueue<Virus> nextPq = new PriorityQueue<>();
            while (!pq.isEmpty()) {
                Virus current = pq.poll();

                int[] dx = {1, 0, -1, 0};
                int[] dy = {0, 1, 0, -1};
                for (int i = 0; i < dx.length; i++) {
                    int nextX = current.x + dx[i];
                    int nextY = current.y + dy[i];

                    // 방문 가능한 노드만 탐색
                    if (nextX >= 1 && nextX <= N && nextY >= 1 && nextY <= N && board[nextX][nextY] == 0) {
                        board[nextX][nextY] = current.type;
                        nextPq.offer(new Virus(nextX, nextY, board[nextX][nextY]));
                    }
                }
            }
            pq = nextPq;
        }
        return board[target.x][target.y];
    }

}
