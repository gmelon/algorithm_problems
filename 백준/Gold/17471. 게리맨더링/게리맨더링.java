import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[] citizenCounts;
	static List<List<Integer>> graph;

	static boolean[] selected;
	static int minDiff = Integer.MAX_VALUE;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		citizenCounts = new int[N + 1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			citizenCounts[i] = Integer.parseInt(st.nextToken());
		}

		graph = new ArrayList<>();
		for(int i = 0 ; i <= N ; i++) {
			graph.add(new ArrayList<>());
		}
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int size = Integer.parseInt(st.nextToken());
			for (int j = 0; j < size; j++) {
				int to = Integer.parseInt(st.nextToken());
				graph.get(i).add(to);
				graph.get(to).add(i);
			}
		}
		br.close();

		// 풀이 시작
		selected = new boolean[N + 1];
		powerSet(1, 0, 0, 0);
		System.out.println(minDiff == Integer.MAX_VALUE ? -1 : minDiff);
	}
	
	static void powerSet(int index, int selectedCount, int selectedSize, int unselectedSize) {
		if (index == N + 1) {
			// 부분 집합 생성 완료
			if (selectedCount == 0 || selectedCount == N) {
				return;
			}
			
			boolean selectedPassed = false, unselectedPassed = false;
			
			for(int i = 1 ; i <= N && (!selectedPassed || !unselectedPassed) ; i++) {
				if (!selectedPassed && selected[i] && bfs(i, true, selectedCount)) {
					selectedPassed = true;
					continue;
				}
				if (!unselectedPassed && !selected[i] && bfs(i, false, N - selectedCount)) {
					unselectedPassed = true;
				}
			}
				
			if (selectedPassed && unselectedPassed) {
				minDiff = Math.min(minDiff, Math.abs(selectedSize - unselectedSize));
			}
			return;
		}
		
		selected[index] = true;
		powerSet(index + 1, selectedCount + 1, selectedSize + citizenCounts[index], unselectedSize);
		selected[index] = false;
		powerSet(index + 1, selectedCount, selectedSize, unselectedSize + citizenCounts[index]);
	}

	static boolean bfs(int start, boolean expectedSelected, int expectedCount) {
		Queue<Integer> queue = new ArrayDeque<>();
		boolean[] visited = new boolean[N + 1];
		
		queue.offer(start);
		visited[start] = true;
		
		int count = 0;
		while(!queue.isEmpty()) {
			int current = queue.poll();
			count++;
			
			for(int neighbor : graph.get(current)) {
				if (visited[neighbor] || selected[neighbor] != expectedSelected) {
					// 다른 집합의 원소면 패스
					continue;
				}
				
				queue.offer(neighbor);
				visited[neighbor] = true;
			}
		}
		
		return count == expectedCount;
	}
	
}