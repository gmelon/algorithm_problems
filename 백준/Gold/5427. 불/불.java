import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;

    static Queue<Position> fires;
    static Queue<Position> 상근이들;
    static boolean[][] firesVisited;
    static boolean[][] 상근Visited;

    static boolean exit = false;

    static class Position {

        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        loop:
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken()); // 열
            N = Integer.parseInt(st.nextToken()); // 행

            fires = new ArrayDeque<>();
            상근이들 = new ArrayDeque<>();
            firesVisited = new boolean[N][M];
            상근Visited = new boolean[N][M];
            exit = false;
            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < M; j++) {
                    char c = line.charAt(j);
                    if (c == '.') {
                        continue;
                    }

                    firesVisited[i][j] = true;
                    상근Visited[i][j] = true;
                    if (c == '*') {
                        // 불
                        fires.offer(new Position(i, j));
                    } else if (c == '@') {
                        // 상근
                        상근이들.offer(new Position(i, j));
                        firesVisited[i][j] = false;
                    }
                }
            }

            int step = 0;
            while (true) {
                step++;
                // 불 이동
                if (fireSpread() == 0 & 상근spread() == 0) {
                    break;
                }

                // 상근이 이동
                if (exit) {
                    sb.append(step).append("\n");
                    continue loop;
                }
            }
            sb.append("IMPOSSIBLE").append("\n");
        }

        System.out.println(sb);
    }

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int fireSpread() {
        int size = fires.size();
        int count = 0;

        while (size-- > 0) {
            Position current = fires.poll();

            for (int d = 0; d < 4; d++) {
                int nX = current.x + dx[d];
                int nY = current.y + dy[d];

                if (nX < 0 || nX >= N || nY < 0 || nY >= M || firesVisited[nX][nY]) {
                    continue;
                }

                // 번지기
                firesVisited[nX][nY] = true;
                상근Visited[nX][nY] = true;
                fires.offer(new Position(nX, nY));
                count++;
            }
        }

        return count;
    }

    static int 상근spread() {
        int size = 상근이들.size();
        int count = 0;

        while (size-- > 0) {
            Position current = 상근이들.poll();

            for (int d = 0; d < 4; d++) {
                int nX = current.x + dx[d];
                int nY = current.y + dy[d];

                if (nX < 0 || nX >= N || nY < 0 || nY >= M) {
                    // 탈출
                    count++;
                    exit = true;
                    continue;
                }

                if (상근Visited[nX][nY]) {
                    continue;
                }

                // 번지기
                상근Visited[nX][nY] = true;
                상근이들.offer(new Position(nX, nY));
                count++;
            }
        }

        return count;
    }
}