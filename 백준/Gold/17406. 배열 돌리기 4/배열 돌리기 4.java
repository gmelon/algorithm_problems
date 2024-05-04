import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int M;
    static int K;

    static int[][] board;
    static int[][] tempBoard;

    static Operation[] operations;
    static Operation[] operationPermutation;
    static boolean[] operationVisited;

    static int answer = Integer.MAX_VALUE;

    static class Operation {
        int startX, startY;
        int endX, endY;
        int round; // 회전 껍데기 개수

        public Operation(String r, String c, String s) {
            this(Integer.parseInt(r) - 1, Integer.parseInt(c) - 1, Integer.parseInt(s));
        }

        private Operation(int r, int c, int s) {
            startX = r - s;
            startY = c - s;
            endX = r + s;
            endY = c + s;
            round = s;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        tempBoard = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                tempBoard[i][j] = board[i][j];
            }
        }

        operations = new Operation[K];
        operationPermutation = new Operation[K];
        operationVisited = new boolean[K];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            operations[i] = new Operation(st.nextToken(), st.nextToken(), st.nextToken());
        }
        br.close();

        // 풀이 시작

        // 수행 가능한 연산의 순열 구하기
        permutation(0);
        System.out.println(answer);
    }

    // 연산의 순열을 계산해서 돌려보기
    static void permutation(int index) {
        if (index == K) {
            // 정해진 연산의 순서대로 배열 돌리기
            for (Operation operation : operationPermutation) {
                rotate(operation, tempBoard);
            }

            // 배열의 '값'을 계산하면서 temp 배열 초기화
            for (int i = 0; i < N; i++) {
                int min = 0;
                for (int j = 0; j < M; j++) {
                    min += tempBoard[i][j];
                    tempBoard[i][j] = board[i][j];
                }
                answer = Math.min(min, answer);
            }
            return;
        }

        for (int i = 0; i < K; i++) {
            if (operationVisited[i]) {
                continue;
            }
            operationVisited[i] = true;
            operationPermutation[index] = operations[i];
            permutation(index + 1);
            operationVisited[i] = false;
        }
    }

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    // 배열 돌리기
    static void rotate(Operation operation, int[][] board) {
        for (int round = 0; round < operation.round; round++) {
            // 시작점 갱신
            int x = operation.startX + round;
            int y = operation.startY + round;

            // 4방향으로 순회하며 당기기
            int temp = board[x][y]; // 시작점의 값을 기록해두기
            for (int d = 0; d < 4; d++) {
                for (int count = 0; count < operation.endX - operation.startX - round * 2; count++) {
                    board[x][y] = board[x + dx[d]][y + dy[d]];
                    x = x + dx[d];
                    y = y + dy[d];
                }
            }

            // 마지막점 채우기
            board[x][y + 1] = temp;
        }
    }
}