import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static class Position {
		int x, y;
		int bit; // a ~ f 열쇠 보유 여부

		public Position(int x, int y, int bit) {
			this.x = x;
			this.y = y;
			this.bit = bit;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		char[][] board = new char[N][M];
		Position start = null;
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				board[i][j] = line.charAt(j);

				if (board[i][j] == '0') {
					start = new Position(i, j, 0);
				}
			}
		}
		br.close();

		// 풀이 시작
		Queue<Position> queue = new ArrayDeque<>();
		boolean[][][] visited = new boolean[N][M][64];

		// init
		queue.offer(start);
		visited[start.x][start.y][start.bit] = true;

		int[] dx = { 1, -1, 0, 0 };
		int[] dy = { 0, 0, 1, -1 };

		int distance = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			distance++;
			while (size-- > 0) {
				Position current = queue.poll();

				for (int d = 0; d < 4; d++) {
					int nX = current.x + dx[d];
					int nY = current.y + dy[d];

					if (nX < 0 || nX >= N || nY < 0 || nY >= M || visited[nX][nY][current.bit]
							|| board[nX][nY] == '#') {
						continue;
					}

					if (board[nX][nY] == '1') {
						System.out.println(distance);
						return;
					}

					if ('A' <= board[nX][nY] && board[nX][nY] <= 'F') {
						// 문
						int bitCount = keyToInt(board[nX][nY]);
						
						if ((current.bit & (1 << bitCount)) == 0) {
							continue;
						}

						visited[nX][nY][current.bit] = true;
						queue.offer(new Position(nX, nY, current.bit));
					}

					if ('a' <= board[nX][nY] && board[nX][nY] <= 'f') {
						// 열쇠
						int bitCount = keyToInt(board[nX][nY]);

						Position next = new Position(nX, nY, (current.bit | (1 << bitCount)));
						visited[next.x][next.y][next.bit] = true;
						queue.offer(next);
					}
					
					if (board[nX][nY] == '.' || board[nX][nY] == '0') {
						visited[nX][nY][current.bit] = true;
						queue.offer(new Position(nX, nY, current.bit));
					}

				}
			}
		}

		System.out.println(-1);
	}

	static int keyToInt(char key) {
		if (key >= 'a') {
			return key - 'a';
		}
		return key - 'A';
	}
}