import java.util.Scanner;

public class Main {

    static int[] board;
    static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();

        // 1차원 배열을 사용하는 버전

        board = new int[n]; // index - 행, 값 - 열

        dfs(0, n);

        System.out.println(count);
    }

    static void dfs(int row, int n) {
        if (row == n) {
            // 마지막 행까지 모두 채움
            count++;
            return;
        }

        for (int col = 0; col < n; col++) {
            board[row] = col;

            if (isPossible(row)) {
                // 현재의 row, col이 가능한 위치면
                // 계속해서 아래 행으로 진행
                dfs(row + 1, n);
            }
        }
    }

    static boolean isPossible(int row) {
        // 열 확인 - 동일한 값이 존재하는지 확인
        for (int i = 0; i < row; i++) {
            if (board[i] == board[row]) return false;
        }

        // 대각선 확인 - 기울기가 같은게 있는지 확인
        for (int i = 0; i < row; i++) {
            if (row - i == Math.abs(board[row] - board[i])) return false;
        }

        return true;
    }
}

