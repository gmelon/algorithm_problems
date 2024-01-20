import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static int time = 0;
    static int[][] board;

    // 상어 정보
    static Shark shark;

    // 먹을 수 있는 물고기 정보
    static int fishRow;
    static int fishCol;
    static int fishDist = 1_000_000_000;

    static class Shark {
        int row, col, size = 2, eatCount = 0;

        public Shark(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void eat() {
            eatCount++;
            if (eatCount == size) {
                size++;
                eatCount = 0;
            }
        }
    }

    static class Position {
        int row, col, dist;

        public Position(int row, int col, int dist) {
            this.row = row;
            this.col = col;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        board = new int[N][N];

        fishRow = N;
        fishCol = N;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = sc.nextInt();

                if (board[i][j] == 9) {
                    shark = new Shark(i, j);
                    board[i][j] = 0;
                }
            }
        }
        sc.close();

        // 풀이 시작
        bfs(); // 주변에 먹을 수 있는 물고기가 있는지 탐색
        while (fishDist != 1_000_000_000) {
            // 있으면 먹기
            board[fishRow][fishCol] = 0;

            time += fishDist;
            shark.row = fishRow;
            shark.col = fishCol;
            shark.eat();

            fishRow = N;
            fishCol = N;
            fishDist = 1_000_000_000;

            bfs(); // 다시 탐색
        }

        // 더이상 먹을 수 있는 물고기가 없음
        System.out.println(time);
    }

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    // 주변에 먹을 수 있는 물고기가 있는지 탐색
    static void bfs() {
        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[board.length][board.length];

        queue.offer(new Position(shark.row, shark.col, 0));
        visited[shark.row][shark.col] = true;

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (current.dist >= fishDist) {
                continue;
            }

            // 주변 탐색
            for (int d = 0; d < dx.length; d++) {
                int nX = current.row + dx[d];
                int nY = current.col + dy[d];

                if (nX < 0 || nX >= board.length || nY < 0 || nY >= board.length || visited[nX][nY]) {
                    continue;
                }

                // 주변을 확인해서 먹을 수 있는 물고기가 있으면 조건에 따라 변수에 할당
                if (board[nX][nY] > 0 && board[nX][nY] < shark.size) {
                    if (nX > fishRow) {
                        continue;
                    }
                    if (nX == fishRow && nY >= fishCol) {
                        continue;
                    }
                    visited[nX][nY] = true;
                    fishRow = nX;
                    fishCol = nY;
                    fishDist = current.dist + 1;
                } else if (board[nX][nY] <= shark.size) {
                    // 먹을 순 없지만 지나갈 순 있으면 지나가기
                    visited[nX][nY] = true;
                    queue.offer(new Position(nX, nY, current.dist + 1));
                }
            }
        }
    }
}
