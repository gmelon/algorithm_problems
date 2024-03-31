import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	// |, -, +, 1, 2, 3, 4 순서
	// 내부적으로는 좌, 상, 우, 하 순서
	static int[][] deltas = { { 1, 3 }, { 0, 2 }, { 0, 1, 2, 3 }, { 2, 3 }, { 1, 2 }, { 0, 1 }, { 0, 3 } };

	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { -1, 0, 1, 0 };

	static class Position {
		int x, y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());

		int[][] map = new int[R][C];
		Position start = null;
		for (int i = 0; i < R; i++) {
			String line = br.readLine();
			for (int j = 0; j < C; j++) {
				char value = line.charAt(j);
				if (value == 'M' || value == 'Z') {
					map[i][j] = 100; // start, target
					start = new Position(i, j);
				} else {
					map[i][j] = typeToInt(value);
				}
			}
		}

		Queue<Position> queue = new ArrayDeque<>();
		boolean[][] visited = new boolean[R][C];

		// init
		queue.offer(start);
		visited[start.x][start.y] = true;

		Position target = null;
		find: while (!queue.isEmpty()) {
			Position current = queue.poll();

			// M 또는 Z라면 주변의 파이프를 찾아 넣는다
			if (map[current.x][current.y] == 100) {
				for (int d = 0; d < 4; d++) {
					int nX = current.x + dx[d];
					int nY = current.y + dy[d];

					if (!isValidRange(nX, nY, R, C) || map[nX][nY] == -1) {
						continue;
					}

					visited[nX][nY] = true;
					queue.offer(new Position(nX, nY));
				}

				continue;
			}

			// 파이프라면,
			int[] delta = deltas[map[current.x][current.y]];
			for (int d = 0; d < delta.length; d++) {
				int nX = current.x + dx[delta[d]];
				int nY = current.y + dy[delta[d]];

				if (!isValidRange(nX, nY, R, C) || visited[nX][nY]) {
					continue;
				}

				if (map[nX][nY] == -1) {
					target = new Position(nX, nY);
					break find;
				}

				visited[nX][nY] = true;
				queue.offer(new Position(nX, nY));
			}
		}

		// 빈 칸을 찾음
		// 빈 칸이 연결되어야 할 방향 찾기

		// 좌, 상, 우, 하 순서로 돌면서 해당하는 타입을 찾는다
		List<Integer> targetDeltas = new ArrayList<>();
		for (int d = 0; d < 4; d++) {
			int nX = target.x + dx[d];
			int nY = target.y + dy[d];

			if (!isValidRange(nX, nY, R, C)) {
				continue;
			}

			if (map[nX][nY] >= 0 && map[nX][nY] <= 6) {
				// 대상 파이프가 현재와 상응하는 방향을 가지고 있어야 함
				for(int oD = 0 ; oD < deltas[map[nX][nY]].length ; oD++) {
					if (deltas[map[nX][nY]][oD] == (d + 2) % 4) {
						targetDeltas.add(d);
						break;
					}
				}
			}
		}

		// list to arr
		int[] targetDeltasArr = new int[targetDeltas.size()];
		for(int i = 0 ; i < targetDeltasArr.length ; i++) {
			targetDeltasArr[i] = targetDeltas.get(i);
		}
		
		char targetType = 0;
		for (int i = 0; i < deltas.length; i++) {
			if (Arrays.equals(deltas[i], targetDeltasArr)) {
				targetType = intToType(i);
			}
		}

		System.out.print((target.x + 1) + " " + (target.y + 1) + " " + targetType);

	}

	static int typeToInt(char type) {
		switch (type) {
		case '.':
			return -1; // 빈칸
		case '|':
			return 0;
		case '-':
			return 1;
		case '+':
			return 2;
		default:
			return type - '1' + 3;
		}
	}

	static char intToType(int type) {
		switch (type) {
		case 0:
			return '|'; // 빈칸
		case 1:
			return '-';
		case 2:
			return '+';
		default:
			return (char) (type - 3 + '1');
		}
	}

	static boolean isValidRange(int x, int y, int R, int C) {
		return x >= 0 && x < R && y >= 0 && y < C;
	}

}