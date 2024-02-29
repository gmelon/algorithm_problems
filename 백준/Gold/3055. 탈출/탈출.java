import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static class Position {
		int x, y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static char[][] map;
	static int N, M;

	static Queue<Position> waters = new ArrayDeque<>();
	static boolean[][] waterVisited;
	static Queue<Position> gosum = new ArrayDeque<>();
	static boolean[][] gosumVisited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		waterVisited = new boolean[N][M];
		gosumVisited = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = line.charAt(j);
				if (map[i][j] == '*') {
					waters.offer(new Position(i, j));
					waterVisited[i][j] = true;
					gosumVisited[i][j] = true;
				}
				if (map[i][j] == 'S') {
					gosum.offer(new Position(i, j));
					gosumVisited[i][j] = true;
				}
			}
		}
		br.close();

		// 풀이 시작
		int date = 0;
		int gosumSpreadCount = 1;
		while (gosumSpreadCount > 0) {
			date++;
			waterSpread();

			if ((gosumSpreadCount = gosumSpread()) < 0) {
				System.out.println(date);
				return;
			}
		}
		System.out.println("KAKTUS");
	}

	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };

	static void waterSpread() {
		int size = waters.size();

		while (size-- > 0) {
			Position current = waters.poll();

			for (int d = 0; d < 4; d++) {
				int nX = current.x + dx[d];
				int nY = current.y + dy[d];

				if (nX < 0 || nX >= N || nY < 0 || nY >= M || waterVisited[nX][nY] || map[nX][nY] == 'X'
						|| map[nX][nY] == 'D') {
					continue;
				}

				waterVisited[nX][nY] = true;
				gosumVisited[nX][nY] = true;
				waters.offer(new Position(nX, nY));
			}
		}
	}

	static int gosumSpread() {
		int size = gosum.size();

		int count = 0;
		while (size-- > 0) {
			Position current = gosum.poll();

			for (int d = 0; d < 4; d++) {
				int nX = current.x + dx[d];
				int nY = current.y + dy[d];

				if (nX < 0 || nX >= N || nY < 0 || nY >= M || gosumVisited[nX][nY] || map[nX][nY] == 'X') {
					continue;
				}

				if (map[nX][nY] == 'D') {
					return -1; // found
				}

				count++;
				gosumVisited[nX][nY] = true;
				gosum.offer(new Position(nX, nY));
			}
		}

		return count;
	}
}