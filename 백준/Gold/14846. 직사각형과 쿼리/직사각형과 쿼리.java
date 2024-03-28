import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 누적합 구하기
        int[][][] acc = new int[11][N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                for (int k = 1; k <= 10; k++) {
                    acc[k][i][j] = acc[k][i - 1][j] + acc[k][i][j - 1] - acc[k][i - 1][j - 1];
                }
                acc[board[i - 1][j - 1]][i][j] += 1;
            }
        }

        // 쿼리 수행
        int Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int q = 0; q < Q; q++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int sX = Integer.parseInt(st.nextToken());
            int sY = Integer.parseInt(st.nextToken());
            int eX = Integer.parseInt(st.nextToken());
            int eY = Integer.parseInt(st.nextToken());

            int count = 0;
            for (int k = 1; k <= 10; k++) {
                if (acc[k][eX][eY] - acc[k][sX - 1][eY] - acc[k][eX][sY - 1] + acc[k][sX - 1][sY - 1] != 0) {
                    count += 1;
                }
            }
            sb.append(count).append("\n");
        }

        System.out.println(sb);
    }

}