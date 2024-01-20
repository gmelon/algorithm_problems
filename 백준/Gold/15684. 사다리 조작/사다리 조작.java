import java.util.Scanner;

public class Main {

    static boolean[][] moveRight;
    static int answer = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int H = sc.nextInt();

        moveRight = new boolean[H + 1][N + 2]; // 1부터 시작

        for (int i = 0; i < M; i++) {
            int row = sc.nextInt();
            int from = sc.nextInt();

            moveRight[row][from] = true;
        }

        sc.close();

        // 풀이 시작
        backtraking(1, 1, 0);

        System.out.println(answer == 4 ? -1 : answer);
    }

    static void backtraking(int row, int col, int depth) {
        if (depth >= 4) {
            // 최대 3개 까지만 가능
            return;
        }

        if (check()) {
            // 현재 상황에서 가능하면 answer 업데이트
            answer = Math.min(answer, depth);
            return;
        }

        // 다시 탐색
        for (int newRow = row; newRow < moveRight.length; newRow++) {
            for (int newCol = 1; newCol < moveRight[0].length - 1; newCol++) {
                // row에선 col 이전까지 무시
                if (newRow == row && newCol < col) {
                    continue;
                }

                // 이미 좌, 우, 현위치에 가로선이 있으면 불가능
                if (moveRight[newRow][newCol] || moveRight[newRow][newCol - 1] || moveRight[newRow][newCol + 1]) {
                    continue;
                }

                moveRight[newRow][newCol] = true;
                backtraking(newRow, newCol, depth + 1);
                moveRight[newRow][newCol] = false;
            }
        }
    }

    // 현재 상황에서 가능한지 확인
    static boolean check() {
        // 모든 col -> col 이 되어야 함
        for (int col = 1; col < moveRight[0].length - 1; col++) {
            int modCol = col;
            for (int row = 1; row < moveRight.length; row++) {
                if (moveRight[row][modCol]) {
                    modCol++;
                } else if(moveRight[row][modCol - 1]) {
                    modCol--;
                }
            }
            if (col != modCol) return false;
        }
        return true;
    }
}
