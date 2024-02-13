import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int max = 0;
    static int[][][] board;
    static boolean[][][] visited;

    static int N; // 행
    static int M; // 열
    static int H; // 높이

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken()); // 열
        N = Integer.parseInt(st.nextToken()); // 행
        H = Integer.parseInt(st.nextToken()); // 높이

        board = new int[N][M][H];
        visited = new boolean[N][M][H];
        for (int curH = 0; curH < H; curH++) {
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    board[i][j][curH] = Integer.parseInt(st.nextToken());
                }
            }
        }
        br.close();

        PriorityQueue<Position> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.count));
        for (int curH = 0; curH < H; curH++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (board[i][j][curH] == 1) {
                        // 방문하지 않은 익은 토마토면 bfs
                        queue.offer(new Position(i, j, curH, 0));
                        visited[i][j][curH] = true;
                    }
                }
            }
        }

        bfs(queue);

        for (int curH = 0; curH < H; curH++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (board[i][j][curH] == 0) {
                        // 모두 익지 못하는 경우
                        System.out.println(-1);
                        return;
                    }
                }
            }
        }

        System.out.println(max);
    }

    static int[] dx = {0, 0, -1, 1, 0, 0};
    static int[] dy = {1, -1, 0, 0, 0, 0};
    static int[] dh = {0, 0, 0, 0, 1, -1};

    static class Position {
        int x, y, h, count;

        public Position(int x, int y, int h, int count) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.count = count;
        }
    }

    static void bfs(PriorityQueue<Position> queue) {
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            max = Math.max(current.count, max);

            for (int d = 0; d < 6; d++) {
                int nX = current.x + dx[d];
                int nY = current.y + dy[d];
                int nH = current.h + dh[d];

                if (nX < 0 || nX >= N || nY < 0 || nY >= M || nH < 0 || nH >= H || visited[nX][nY][nH] || board[nX][nY][nH] == -1) {
                    continue;
                }

                visited[nX][nY][nH] = true;
                if (board[nX][nY][nH] == 1) {
                    queue.offer(new Position(nX, nY, nH, current.count));
                } else {
                    board[nX][nY][nH] = 1; // 익히기
                    queue.offer(new Position(nX, nY, nH, current.count + 1));
                }
            }
        }
    }

}