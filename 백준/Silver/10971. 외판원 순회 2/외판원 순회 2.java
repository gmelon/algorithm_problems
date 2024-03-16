import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[][] graph;
	static boolean[] visited;

	static int minCost = Integer.MAX_VALUE;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		graph = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();

		visited = new boolean[N];
		for (int i = 0; i < N; i++) {
			
		}
        
        dfs(1, 0, 0, 0);

		System.out.println(minCost);
	}

	static void dfs(int index, int prev, int start, int cost) {
		if (cost > minCost) {
			return;
		}
		if (index == N) {
			if (graph[prev][start] == 0) {
				return;
			}
			minCost = Math.min(minCost, cost + graph[prev][start]);
			return;
		}
		
		visited[prev] = true;
		for (int i = 0; i < N; i++) {
			if (visited[i] || graph[prev][i] == 0) {
				continue;
			}
			dfs(index + 1, i, start, cost + graph[prev][i]);
		}
		visited[prev] = false;
	}
}