import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 일반 땅과 사람 두 명을 각각 큐에 담는다
	// 3경우에 모두 (별도의) visited 배열을 만든다
	// 먼저 빙하를 녹이고 각 사람을 움직이게 한다
	// 사람이 움직이며 다른 사람의 visited에 도달한 경우 만난 것으로 간주한다

	static class Position {
		int x, y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static Queue<Position> grounds = new ArrayDeque<>();
	static Queue<Position> personA = new ArrayDeque<>();
	static Queue<Position> personB = new ArrayDeque<>();

	static boolean[][] groundVisited;
	static boolean[][] personAVisited;
	static boolean[][] personBVisited;

	static char[][] board;

	static int R;
	static int C;
	
	static boolean finished = false; // 만남 여부

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		// 배열 초기화
		groundVisited = new boolean[R][C];
		personAVisited = new boolean[R][C];
		personBVisited = new boolean[R][C];

		board = new char[R][C];

		// 보드 입력
		for (int i = 0; i < R; i++) {
			String line = br.readLine();
			for (int j = 0; j < C; j++) {
				board[i][j] = line.charAt(j);
				if (board[i][j] == '.' || board[i][j] == 'L') {
					// 일반 땅
					groundVisited[i][j] = true;
					grounds.offer(new Position(i, j));
				}
				if (board[i][j] == 'L') {
					// 사람
					if (personA.isEmpty()) {
						// A
						personAVisited[i][j] = true;
						personA.offer(new Position(i, j));
					} else {
						// B
						personBVisited[i][j] = true;
						personB.offer(new Position(i, j));
					}
				}
			}
		}
		br.close();

		// 풀이 시작
		// 빙하는 하루에 한 라운드씩 녹지만,
		// 사람은 이동할 수 있는 최대로 이동
		int step = 0;
		while (true) {
			step++;
			// 먼저 빙하 녹이기
			melting();
			
			// A 이동
			moveA();
			if (finished) {
				break;
			}
			
			// B 이동
			moveB();
			if (finished) {
				break;
			}
		}
		
		System.out.println(step);
	}

	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };

	static void melting() {
		int size = grounds.size();

		// 한 뎁스씩 돌기
		while (size-- > 0) {
			Position current = grounds.poll();

			for (int d = 0; d < 4; d++) {
				int nX = current.x + dx[d];
				int nY = current.y + dy[d];

				if (nX < 0 || nX >= R || nY < 0 || nY >= C || groundVisited[nX][nY]) {
					continue;
				}

				if (board[nX][nY] == 'X') {
					// 빙하면 녹이기
					board[nX][nY] = '.';
					groundVisited[nX][nY] = true;
					grounds.offer(new Position(nX, nY));
				}
			}
		}
	}

	static void moveA() {
		Queue<Position> newPersonA = new ArrayDeque<>();
		boolean[][] checked = new boolean[R][C];
		
		while (!personA.isEmpty()) {
			Position current = personA.poll();

			for (int d = 0; d < 4; d++) {
				int nX = current.x + dx[d];
				int nY = current.y + dy[d];

				if (nX < 0 || nX >= R || nY < 0 || nY >= C) {
					continue;
				}

				if (personBVisited[nX][nY]) {
					// A가 B를 만나면
					finished = true;
					return;
				}
				
				if (personAVisited[nX][nY]) {
					// 중복
					continue;
				}
				
				if (board[nX][nY] == 'X' && !checked[current.x][current.y]) {
					// 빙하로 막혔다면 새로운 큐에 '한번만' 넣어줌
					checked[current.x][current.y] = true;
					newPersonA.offer(new Position(current.x, current.y));
				}

				if (board[nX][nY] == '.') {
					// 일반땅이면 A 이동
					personAVisited[nX][nY] = true;
					personA.offer(new Position(nX, nY));
				}
			}
		}
		
		personA = newPersonA;
	}
	
	static void moveB() {
		Queue<Position> newPersonB = new ArrayDeque<>();
		boolean[][] checked = new boolean[R][C];
		
		while (!personB.isEmpty()) {
			Position current = personB.poll();

			for (int d = 0; d < 4; d++) {
				int nX = current.x + dx[d];
				int nY = current.y + dy[d];

				if (nX < 0 || nX >= R || nY < 0 || nY >= C) {
					continue;
				}

				if (personAVisited[nX][nY]) {
					// B가 A를 만나면
					finished = true;
					return;
				}
				
				if (personBVisited[nX][nY]) {
					// 중복
					continue;
				}
				
				if (board[nX][nY] == 'X' && !checked[current.x][current.y]) {
					// 빙하로 막혔다면 새로운 큐에 '한번만' 넣어줌
					checked[current.x][current.y] = true;
					newPersonB.offer(new Position(current.x, current.y));
				}

				if (board[nX][nY] == '.') {
					// 일반땅이면 B 이동
					personBVisited[nX][nY] = true;
					personB.offer(new Position(nX, nY));
				}
			}
		}
		
		personB = newPersonB;
	}
}