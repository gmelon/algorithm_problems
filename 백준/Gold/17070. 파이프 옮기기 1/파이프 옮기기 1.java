import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[][] map; // 0 - 빈칸, 1 - 벽

	static int[][][] dp;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		dp = new int[N][N][3];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				Arrays.fill(dp[i][j], -1);
			}
		}
		br.close();

		System.out.println(dfs(0, 1, 0));
	}

	// 현재 방향이 가로(0), 세로(1), 대각선(2)일 때 가능한 이동 방향
	// 각 이동 방향의 마지막 index 볼 때는 대각선 이동 -> 3칸 가능 여부 확인하기
	static int[][] dx = { { 0, -1, 1 }, { -1, 1, 1 }, { 0, 1, 1 } };
	static int[][] dy = { { 1, -1, 1 }, { -1, 0, 1 }, { 1, 0, 1 } };

	static int dfs(int x, int y, int curType) {
		if (x == N - 1 && y == N - 1) {
			return 1; // 목표 지점 도달, 한 가지 방법 찾음
		}

		if (dp[x][y][curType] != -1) {
			return dp[x][y][curType];
		}

		// 현재 파이프가 놓여진 방향에 따라 가능한 방향을 확인
		int count = 0;
		for (int d = 0; d < 3; d++) {
			if (dx[curType][d] == -1) {
				// 현재 타입에서 지원하지 않는 방향
				continue;
			}

			int nX = x + dx[curType][d];
			int nY = y + dy[curType][d];

			if (nX < 0 || nX >= N || nY < 0 || nY >= N) {
				continue;
			}

			// 이동 가능 여부 판단
			if (d == 2) {
				// 대각선 방향
				if (map[nX][nY] == 1 || map[nX - 1][nY] == 1 || map[nX][nY - 1] == 1) {
					continue;
				}
				count += dfs(nX, nY, d);
			} else {
				// 가로, 세로 방향
				if (map[nX][nY] == 1) {
					continue;
				}
				count += dfs(nX, nY, d);
			}
		}
		return dp[x][y][curType] = count;
	}
}