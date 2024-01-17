import java.util.Scanner;

public class Main {
    static int count = 1_000;
    static boolean[][] move;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 세로선의 개수
        int M = sc.nextInt(); // 기존 가로선의 개수
        int H = sc.nextInt(); // 가로선을 놓을 수 있는 위치의 개수

        move = new boolean[H + 1][N + 2];
        for (int i = 0; i < M; i++) {
            int direction = sc.nextInt();
            int from = sc.nextInt();
            move[direction][from] = true; // 우측으로 이동
        }
        sc.close();

        // 풀이 시작
        dfs(1, 1, 0);

        System.out.println(count == 1_000 ? -1 : count);
    }

    static void dfs(int row, int col, int depth) {
        if (depth == 4) return;

        // 가능한지 검사
        if (check()) {
            // 가능하면 count = depth 후 리턴
            count = Math.min(depth, count);
        } else {
            // 불가능하면 depth + 1로 다시 탐색
            for(int x = row ; x < move.length; x++) {
                for (int y = 1; y < move[0].length - 1; y++) {
                    if (move[x][y] || move[x][y - 1] || move[x][y + 1]) continue;

                    // 현재 위치에 가로선 연결
                    move[x][y] = true;
                    dfs(x, y, depth + 1);
                    move[x][y] = false;
                }
            }
        }
    }

    static boolean check() {
        // 모든 열이 i -> i로 나와야 함
        for (int col = 1; col < move[0].length - 1; col++) {
            int curY = col;
            for (int row = 1; row < move.length; row++) {
                if (move[row][curY - 1])
                    curY--;
                else if (move[row][curY])
                    curY++;
            }
            if (col != curY)
                return false;
        }
        return true;
    }
}
