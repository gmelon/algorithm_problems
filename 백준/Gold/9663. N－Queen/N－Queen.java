import java.util.Scanner;

public class Main {

    static boolean[][] visited;
    static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();

        visited = new boolean[n][n];

        // 첫째행 모든 열에 대하여 탐색
        for(int y = 0 ; y < n ; y++) {
            visited[0][y] = true;
            dfs(0, y);
            visited[0][y] = false;
        }

        System.out.println(count);
    }

    static void dfs(int x, int y) {
        // 행은 무조건 가능
        // 열 검사
        if (isPossible(x, y)) {
            // 마지막 행이면 count++ 후 종료
            if (x == visited.length - 1) {
                count++;
                return;
            }
            // 가능하면, 다시 아래 행의 모든 열 탐색
            for(int i = 0 ; i < visited.length ; i++) {
                visited[x + 1][i] = true;
                dfs(x + 1, i);
                visited[x + 1][i] = false;
            }
        }
        // 불가능하다면 반환
    }

    // 좌상, 우상
    static int[] dx = {-1, -1};
    static int[] dy = {-1, 1};

    static boolean isPossible(int x, int y) {
        // 열 검사 (현재 행 이전까지만 검사하면 됨)
        for(int i = 0 ; i < x ; i++) {
            if (visited[i][y]) return false;
        }

        // 대각선 검사 - 현재 행 기준 좌상 & 우상만 검사하면 됨
        for(int d = 0 ; d < dx.length ; d++) {
            int nX = x;
            int nY = y;
            for (int i = x - 1; i >= 0; i--) {
                nX += dx[d];
                nY += dy[d];

                // 범위
                if (nX < 0 || nX >= visited.length || nY < 0 || nY >= visited.length) {
                    continue;
                }

                // visited
                if (visited[nX][nY]) return false;
            }
        }
        return true;
    }
}

