import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int R;
    static int C;
    static int[][] board;
    static boolean[][] visited;

    static int maxCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        board = new int[R][C]; // -1이면 벽
        visited = new boolean[R][C];
        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                board[i][j] = line.charAt(j) == '.' ? 0 : -1;
            }
        }
        br.close();

        for (int i = 0; i < R; i++) {
            dfs(i, 0);
        }
        System.out.println(maxCount);
    }

    static int[] dx = {-1, 0, 1};

    static boolean dfs(int x, int y) {
        if (y == C - 1) {
            maxCount++;
            return true;
        }

        for (int d = 0; d < 3; d++) {
            int nX = x + dx[d];
            int nY = y + 1;

            if (nX < 0 || nX >= R || nY < 0 || nY >= C || board[nX][nY] == -1) {
                continue;
            }

            if (visited[nX][nY] || ((nX < R - 1 && visited[nX + 1][nY]) && (nY >= 1 && visited[nX][nY - 1]))) {
                continue;
            }

            visited[nX][nY] = true;
            boolean result = dfs(nX, nY);
            if (result) {
                return true;
            } else {
//                visited[nX][nY] = false;
            }
        }
        return false;
    }

}