import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static class Position {
        int x, y, count;

        public Position(int x, int y, int count) {
            this.x = x;
            this.y = y;
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Position start = new Position(sc.nextInt(), sc.nextInt(), 0);
        Position target = new Position(sc.nextInt(), sc.nextInt(), 0);

        sc.close();

        System.out.println(bfs(start, target));
    }

    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    static int[] dcx = {1, 1, 0, 0};
    static int[] dcy = {0, 0, 1, 1};

    static int bfs(Position start, Position target) {
        Queue<Position> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[10][9];

        queue.offer(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            if (current.equals(target)) {
                return current.count;
            }

            // 모든 방향 탐색
            for (int d = 0; d < 4; d++) {
                int nX = current.x + dx[d];
                int nY = current.y + dy[d];

                // 경로에 target이 있으면 불가능
                if (nX == target.x && nY == target.y) {
                    continue;
                }

                // 대각선 이동
                for (int c = -1; c <= 1; c += 2) {
                    int ncX = nX + dx[d] + c * dcx[d];
                    int ncY = nY + dy[d] + c * dcy[d];

                    // 경로에 target이 있으면 불가능
                    if (ncX == target.x && ncY == target.y) {
                        continue;
                    }

                    ncX += dx[d] + c * dcx[d];
                    ncY += dy[d] + c * dcy[d];

                    if (isInvalidRange(ncX, ncY, visited)) {
                        continue;
                    }

                    visited[ncX][ncY] = true;
                    queue.offer(new Position(ncX, ncY, current.count + 1));
                }
            }
        }
        return -1; // 찾지 못함
    }

    static boolean isInvalidRange(int nX, int nY, boolean[][] visited) {
        return nX < 0 || nX >= 10 || nY < 0 || nY >= 9 || visited[nX][nY];
    }


}