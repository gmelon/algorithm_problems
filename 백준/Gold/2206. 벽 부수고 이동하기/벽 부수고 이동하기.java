import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static boolean[][] visitable;
	static int N;
	static int M;

	static boolean[][] brokeVisited;
	static boolean[][] normalVisited;

	static int minDistance = -1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		brokeVisited = new boolean[N][M];
		normalVisited = new boolean[N][M];
		visitable = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				visitable[i][j] = line.charAt(j) == '0' ? true : false;
			}
		}
		br.close();

		// 풀이 시작
		bfs();

		System.out.println(minDistance);
	}

	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };

	static class Position {
		int x, y, count;
		boolean broked;

		public Position(int x, int y, int count, boolean broked) {
			this.x = x;
			this.y = y;
			this.count = count;
			this.broked = broked;
		}
	}

	static void bfs() {
		Queue<Position> queue = new ArrayDeque<>();
		queue.offer(new Position(0, 0, 1, false));
		normalVisited[0][0] = true;

		while (!queue.isEmpty()) {
			Position current = queue.poll();

			if (current.x == N - 1 && current.y == M - 1) {
				minDistance = current.count;
				return;
			}

			for (int d = 0; d < 4; d++) {
				int nX = current.x + dx[d];
				int nY = current.y + dy[d];

				if (nX < 0 || nX >= N || nY < 0 || nY >= M) {
					continue;
				}

				if (visitable[nX][nY]) {
					// 다음 칸이 빈칸인 경우
					if (current.broked) {
						// 이미 벽을 부순 경우
						if (!brokeVisited[nX][nY]) {
							brokeVisited[nX][nY] = true;
							queue.offer(new Position(nX, nY, current.count + 1, true));
						}
					} else {
						// 벽을 부수지 않은 경우
						if (!normalVisited[nX][nY]) {
							normalVisited[nX][nY] = true;
							queue.offer(new Position(nX, nY, current.count + 1, false));
						}
					}
				} else {
					// 다음 칸이 벽인 경우
					if (!current.broked) {
						// 벽을 부수지 않은 경우만 가능 (벽을 부수면서 진행)
						if (!brokeVisited[nX][nY]) {
							brokeVisited[nX][nY] = true;
							queue.offer(new Position(nX, nY, current.count + 1, true));
						}
					}
				}
			}
		}
	}
}