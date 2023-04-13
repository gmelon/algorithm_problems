import java.util.*;

class Solution {

    public int solution(int n, int[][] wires) {
        // 각 간선이 없다고 생각하고 그때마다의 전체 연결된 노드의 개수 합을 세서 (dfs)
        // 그 차이가 가장 작을 때를 답으로 반환한다

        int answer = Integer.MAX_VALUE;

        // 전체 그래프 생성
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] wire : wires) {
            int nodeA = wire[0];
            int nodeB = wire[1];

            // 양방향으로 등록
            if (graph.get(nodeA) == null) {
                graph.put(nodeA, new ArrayList<>());
            }
            graph.get(nodeA).add(nodeB);

            if (graph.get(nodeB) == null) {
                graph.put(nodeB, new ArrayList<>());
            }
            graph.get(nodeB).add(nodeA);
        }

        // 하나의 간선씩 제외하며 dfs 순회하여 최소 값 비교하기
        for (int[] wire : wires) {
            // 간선 제거
            graph.get(wire[0]).remove(Integer.valueOf(wire[1]));
            graph.get(wire[1]).remove(Integer.valueOf(wire[0]));

            // 각 노드를 시작으로 하여 각 트리의 노드 개수 확인
            int diff = dfs(n, graph, wire[0]) - dfs(n, graph, wire[1]);
            // diff의 절대값의 최소값으로 answer를 갱신
            answer = Math.min(answer, Math.abs(diff));

            // 간선 다시 추가
            graph.get(wire[0]).add(wire[1]);
            graph.get(wire[1]).add(wire[0]);
        }

        return answer;
    }

    public int dfs(final int n, final Map<Integer, List<Integer>> graph, int startNode) {
        int count = 0;
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[n + 1];

        stack.push(startNode);

        while(!stack.isEmpty()) {
            Integer current = stack.pop();
            count++;
            visited[current] = true;

            if (graph.get(current) == null) {
                continue;
            }

            for (Integer neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }

        return count;
    }

}