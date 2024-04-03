import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int R, C, T;
	static int[][] map;

	static int airRow;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken()); // 행
		C = Integer.parseInt(st.nextToken()); // 열
		T = Integer.parseInt(st.nextToken()); // 시간

		airRow = -1;

		map = new int[R][C];
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());

				if (map[i][j] == -1 && airRow == -1) {
					airRow = i;
				}
			}
		}
		br.close();

		for (int t = 0; t < T; t++) {
			// 미세먼지 확산
			spread();

			// 공기 청정
			freshAir();
		}

		// 미세먼지 양 세기
		int count = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				count += map[i][j];
			}
		}
		System.out.println(count + 2);
	}

	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { 1, 0, -1, 0 };

	static void spread() {
		int[][] operationMap = new int[R][C];
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] <= 0) {
					continue;
				}

				int count = 0;
				for (int d = 0; d < 4; d++) {
					int nX = i + dx[d];
					int nY = j + dy[d];

					if (nX < 0 || nX >= R || nY < 0 || nY >= C || map[nX][nY] == -1) {
						continue;
					}

					// 퍼트리기
					operationMap[nX][nY] += map[i][j] / 5;
					count++;
				}

				operationMap[i][j] += map[i][j] - (map[i][j] / 5) * count;
			}
		}
		map = operationMap;
	}

	static void freshAir() {
		// 시계 방향
		int x = airRow;
		int y = 0;
		int dCount = 0;
		int d = 1;
		while (dCount < 4) {
			int nX = x + dx[d];
			int nY = y + dy[d];

			if (!checkRange(nX, nY) || nX > airRow) {
				dCount++;
				d = (d + 3) % 4;
				continue;
			}

			map[x][y] = map[nX][nY];
			x = nX;
			y = nY;
		}
		map[airRow][0] = -1;
		map[airRow][1] = 0;

		// 반시계 방향
		x = airRow + 1;
		y = 0;
		dCount = 0;
		d = 3;
		while (dCount < 4) {
			int nX = x + dx[d];
			int nY = y + dy[d];

			if (!checkRange(nX, nY) || nX < airRow + 1) {
				dCount++;
				d = (d + 1) % 4;
				continue;
			}

			map[x][y] = map[nX][nY];
			x = nX;
			y = nY;
		}
		map[airRow + 1][0] = -1;
		map[airRow + 1][1] = 0;
	}

	static boolean checkRange(int x, int y) {
		return x >= 0 && x < R && y >= 0 && y < C;
	}
}