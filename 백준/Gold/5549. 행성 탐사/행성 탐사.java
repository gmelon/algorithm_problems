import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static class Target {
        int fromX, fromY, toX, toY;

        public Target(int fromX, int fromY, int toX, int toY) {
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(br.readLine());
        char[][] map = new char[M + 1][N + 1];
        for (int i = 1; i <= M; i++) {
            String line = br.readLine();
            for (int j = 1; j <= N; j++) {
                map[i][j] = line.charAt(j - 1);
            }
        }

        Target[] targets = new Target[K];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            targets[i] = new Target(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        br.close();

        int[][] 정글count = new int[M + 1][N + 1];
        int[][] 바다count = new int[M + 1][N + 1];
        int[][] 얼음count = new int[M + 1][N + 1];

        // 누적합 만들기
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                정글count[i][j] = 정글count[i - 1][j] + 정글count[i][j - 1] - 정글count[i - 1][j - 1];
                바다count[i][j] = 바다count[i - 1][j] + 바다count[i][j - 1] - 바다count[i - 1][j - 1];
                얼음count[i][j] = 얼음count[i - 1][j] + 얼음count[i][j - 1] - 얼음count[i - 1][j - 1];

                if (map[i][j] == 'J') {
                    정글count[i][j] += 1;
                }
                if (map[i][j] == 'O') {
                    바다count[i][j] += 1;
                }
                if (map[i][j] == 'I') {
                    얼음count[i][j] += 1;
                }
            }
        }

        // 구역별 합계 계산하기
        for (Target target : targets) {
            int 정글 = 정글count[target.toX][target.toY] - 정글count[target.fromX - 1][target.toY] - 정글count[target.toX][target.fromY - 1]
                + 정글count[target.fromX - 1][target.fromY - 1];
            int 바다 = 바다count[target.toX][target.toY] - 바다count[target.fromX - 1][target.toY] - 바다count[target.toX][target.fromY - 1]
                + 바다count[target.fromX - 1][target.fromY - 1];
            int 얼음 = 얼음count[target.toX][target.toY] - 얼음count[target.fromX - 1][target.toY] - 얼음count[target.toX][target.fromY - 1]
                + 얼음count[target.fromX - 1][target.fromY - 1];
            System.out.println(정글 + " " + 바다 + " " + 얼음);
        }
    }

}
