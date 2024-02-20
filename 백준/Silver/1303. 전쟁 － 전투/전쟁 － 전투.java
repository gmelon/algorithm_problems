import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static char[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        board = new char[N][M];
        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }
        br.close();

        // 풀이 시작
        int myCount = 0;
        int yourCount = 0;
        boolean[][] visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visited[i][j]) {
                    continue;
                }

                int count = (int) Math.pow(bfs(i, j, visited), 2);
                if (board[i][j] == 'W') {
                    myCount += count;
                } else {
                    yourCount += count;
                }
            }
        }

        System.out.println(myCount + " " + yourCount);
    }

    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int bfs(int x, int y, boolean[][] visited) {
        Queue<Position> queue = new ArrayDeque<>();
        visited[x][y] = true;
        queue.offer(new Position(x, y));

        int count = 0;
        char type = board[x][y];
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            count++;

            for (int d = 0; d < 4; d++) {
                int nX = current.x + dx[d];
                int nY = current.y + dy[d];

                if (nX < 0 || nX >= visited.length || nY < 0 || nY >= visited[0].length || visited[nX][nY]) {
                    continue;
                }

                if (board[nX][nY] != type) {
                    continue;
                }

                visited[nX][nY] = true;
                queue.offer(new Position(nX, nY));
            }
        }

        return count;
    }


}