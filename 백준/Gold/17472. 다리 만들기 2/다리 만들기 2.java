import java.io.*;
import java.util.*;

public class Main {

	static class Position {
		int x, y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	static class Edge implements Comparable<Edge> {
		int from, to, weight;

		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
        
        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
	}

	static int N, M;
	static int[][] map;
	static List<List<Position>> islands;
	static boolean[][] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();

		// 모든 섬 찾기
		islands = new ArrayList<>();
		visited = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 1 && !visited[i][j]) {
					islands.add(findIsland(new Position(i, j)));
				}
			}
		}

		// 모든 섬 사이의 최소 가중치 간선 구하기
		// 다리 연결은 가로 또는 세로만 가능
		List<Edge> edges = new ArrayList<>();
		for (int from = 0; from < islands.size(); from++) {
			for (int to = from + 1; to < islands.size(); to++) {
				List<Position> fromLand = islands.get(from);
				List<Position> toLand = islands.get(to);

				int distance = 1_000; // 1_000 이면 다리 업슴

				// 모든 좌표 조합에 대하여 가능한 가중치의 최솟값을 구하기
				for (Position fromLandPosition : fromLand) {
					for (Position toLandPosition : toLand) {
						// 가로 연결이 가능한가?
						if (fromLandPosition.x == toLandPosition.x
								&& Math.abs(fromLandPosition.y - toLandPosition.y) > 2) {
							if (fromLandPosition.y < toLandPosition.y) {
								if (checkColEmpty(fromLandPosition.x, fromLandPosition.y, toLandPosition.y)) {
									// 가능
									distance = Math.min(distance, toLandPosition.y - fromLandPosition.y - 1);
								}
							} else {
								if (checkColEmpty(fromLandPosition.x, toLandPosition.y, fromLandPosition.y)) {
									// 가능
									distance = Math.min(distance, fromLandPosition.y - toLandPosition.y - 1);
								}
							}
						}

						// 세로 연결이 가능한가?
						if (fromLandPosition.y == toLandPosition.y
								&& Math.abs(fromLandPosition.x - toLandPosition.x) > 2) {
							if (fromLandPosition.x < toLandPosition.x) {
								if (checkRowEmpty(fromLandPosition.y, fromLandPosition.x, toLandPosition.x)) {
									// 가능
									distance = Math.min(distance, toLandPosition.x - fromLandPosition.x - 1);
								}
							} else {
								if (checkRowEmpty(fromLandPosition.y, toLandPosition.x, fromLandPosition.x)) {
									// 가능
									distance = Math.min(distance, fromLandPosition.x - toLandPosition.x - 1);
								}
							}
						}
					}
				}

				if (distance < 1_000) {
					// 간선이 존재하면 추가
					edges.add(new Edge(from, to, distance));
				}
			}
		}

		// 모든 섬 사이의 최소 가중치 간선 구하기 완료
		// 이제 크루스칼 할 차례..

		int[] parents = new int[islands.size()];
		for (int i = 0; i < parents.length; i++) {
			parents[i] = i;
		}

        Collections.sort(edges);

		int answer = 0;

		for (Edge edge : edges) {
			int pF = findParent(parents, edge.from);
			int pT = findParent(parents, edge.to);

			if (pF == pT) {
				// 사이클
				continue;
			}

			// 병합
			union(parents, pF, pT);
			answer += edge.weight;
		}

		// 모든 섬이 연결되었는지 확인
		for (int i = 0; i < parents.length - 1; i++) {
			if (findParent(parents, i) != findParent(parents, i + 1)) {
				System.out.println(-1);
				return;
			}
		}

		System.out.println(answer);
	}

	static boolean checkRowEmpty(int col, int startRow, int endRow) {
		for (int row = startRow + 1; row < endRow; row++) {
			if (map[row][col] == 1) {
				return false;
			}
		}
		return true;
	}

	static boolean checkColEmpty(int row, int startCol, int endCol) {
		for (int col = startCol + 1; col < endCol; col++) {
			if (map[row][col] == 1) {
				return false;
			}
		}
		return true;
	}

	static int findParent(int[] parents, int target) {
		if (parents[target] != target) {
			parents[target] = findParent(parents, parents[target]);
		}
		return parents[target];
	}

	static void union(int[] parents, int a, int b) {
		int pA = findParent(parents, a);
		int pB = findParent(parents, b);

		// union
		parents[pA] = pB;
	}

	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };

	static List<Position> findIsland(Position start) {
		Queue<Position> queue = new ArrayDeque<>();

		queue.offer(start);
		visited[start.x][start.y] = true;

		// 섬 초기화
		List<Position> island = new ArrayList<>();

		while (!queue.isEmpty()) {
			Position current = queue.poll();

			// 섬 정보 갱신
			island.add(current);

			for (int d = 0; d < 4; d++) {
				int nX = current.x + dx[d];
				int nY = current.y + dy[d];

				if (nX < 0 || nX >= N || nY < 0 || nY >= M || visited[nX][nY] || map[nX][nY] != 1) {
					continue;
				}

				visited[nX][nY] = true;
				queue.offer(new Position(nX, nY));
			}
		}

		return island;
	}
}