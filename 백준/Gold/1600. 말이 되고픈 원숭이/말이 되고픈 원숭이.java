import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 결국 말의 이동 방향 8개를 포함해 delta가 12개인 셈

	static int[] dr = { 1, -1, 0, 0 };
	static int[] dc = { 0, 0, 1, -1 };

	static int K;
	static int R, C;
	static boolean[][][] visited; // visited[r][c][k] = r,c에 k번의 말을 사용해 방문한 여부
	static boolean[][] walls; // 장애물

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		K = Integer.parseInt(br.readLine()); // 말 처럼 이동할 수 있는 최대 횟수
		StringTokenizer st = new StringTokenizer(br.readLine());
		C = Integer.parseInt(st.nextToken()); // 열 길이
		R = Integer.parseInt(st.nextToken()); // 행 길이
		visited = new boolean[R][C][K + 1];
		walls = new boolean[R][C];
		for (int r = 0; r < R; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < C; c++) {
				if (st.nextToken().charAt(0) == '1') {
					// 장애물
					walls[r][c] = true;
				}
			}
		}
		br.close();

		System.out.println(bfs());
	}

	static class Position {
		int r, c, kCount;

		public Position(int r, int c, int kCount) {
			this.r = r;
			this.c = c;
			this.kCount = kCount;
		}
	}

	static int bfs() {
		Queue<Position> queue = new ArrayDeque<>();
		// init, 최초에는 k를 소진하지 않고 초기점에 위치함
		queue.offer(new Position(0, 0, 0));
		visited[0][0][0] = true;

		int count = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				Position current = queue.poll();

				// 목적지에 도달
				if (current.r == R - 1 && current.c == C - 1) {
					return count;
				}

				// 기본 이동
				for (int d = 0; d < 4; d++) {
					int nR = current.r + dr[d];
					int nC = current.c + dc[d];

					if (isRangeValid(nR, nC) && !walls[nR][nC] && !visited[nR][nC][current.kCount]) {
						visited[nR][nC][current.kCount] = true;
						queue.offer(new Position(nR, nC, current.kCount));
					}

					// 아직 말처럼 이동할 수 있는 횟수가 남아있음
					if (current.kCount < K) {
						int horseDelta = 0;
						if (d <= 1) {
							horseDelta = 2;
						}
						for (int hd = 0; hd < 2; hd++) {
							int hR = nR + dr[d] + dr[horseDelta + hd];
							int hC = nC + dc[d] + dc[horseDelta + hd];

							if (isRangeValid(hR, hC) && !walls[hR][hC] && !visited[hR][hC][current.kCount + 1]) {
								visited[hR][hC][current.kCount + 1] = true;
								queue.offer(new Position(hR, hC, current.kCount + 1));
							}
						}
					}
				}
			}
			count++;
		}
		// 마지막까지 도달하지 못함
		return -1;
	}

	static boolean isRangeValid(int r, int c) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}
}