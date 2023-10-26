import java.util.Scanner;

public class Main {

    static int[][] board;
    static boolean[][] visited;
    static int answer = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        board = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = sc.nextInt();
            }
        }

        sc.close();

        // 풀이 시작
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 1, board[i][j]);
                visited[i][j] = false;
                neighbor(i, j, 0, 0, board[i][j]);
            }
        }

        System.out.println(answer);
    }

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static void dfs(int curX, int curY, int depth, int sum) {
        if (depth == 4) {
            answer = Math.max(answer, sum);
            return;
        }

        for (int i = 0; i < dx.length; i++) {
            int nextX = curX + dx[i];
            int nextY = curY + dy[i];

            if (isAble(nextX, nextY) && !visited[nextX][nextY]) {
                visited[nextX][nextY] = true;
                dfs(nextX, nextY, depth + 1, sum + board[nextX][nextY]);
                visited[nextX][nextY] = false;
            }
        }
    }

    static void neighbor(int centerX, int centerY, int count, int index, int sum) {
        if (count == 3) {
            answer = Math.max(answer, sum);
            return;
        }

        for (int i = index; i < dx.length; i++) {
            int nextX = centerX + dx[i];
            int nextY = centerY + dy[i];

            if (isAble(nextX, nextY)) {
                neighbor(centerX, centerY, count + 1, i + 1, sum + board[nextX][nextY]);
            }
        }
    }

    static boolean isAble(int x, int y) {
        return x >= 0 && y >= 0 && x < board.length && y < board[0].length;
    }

}
