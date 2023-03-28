import java.util.*;

class Solution {
    
    static class Node implements Comparable<Node>{
        int index;
        int currentDistance;
        
        Node(int index, int currentDistance) {
            this.index = index;
            this.currentDistance = currentDistance;
        }
        
        @Override
        public int compareTo(Node other) {
            return this.currentDistance - currentDistance;
        }
    }
    
    public int solution(int n, int[][] edge) {
        int[] distances = new int[n + 1]; // 매 시점 노드까지의 최단거리를 저장
        Arrays.fill(distances, Integer.MAX_VALUE);
        
        List<List<Integer>> graph = new ArrayList<>(); // 각 노드와 연결된 노드 리스트
        
        // graph 초기화
        for (int i = 0 ; i <= n ; i++) {
            graph.add(new ArrayList<>());
        }
        // 간선 리스트 추가 (양방향)
        for (int[] e : edge) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        
        // 다익스트라 알고리즘 적용
        PriorityQueue<Node> queue = new PriorityQueue<>();
        // 초기 (1번) 노드 설정
        queue.offer(new Node(1, 0)); 
        distances[1] = 0;
        
        while(!queue.isEmpty()) {
            Node currentNode = queue.poll();
            // 이전에 처리한 노드면 제외
            if (currentNode.currentDistance > distances[currentNode.index]) {
                continue;
            }
            
            // 연결된 노드들 갱신
            for (int neighbor : graph.get(currentNode.index)) {
                int newDistance = distances[currentNode.index] + 1;
                // 새로운 경로가 더 적을 때만 갱신 및 큐에 추가
                if (newDistance < distances[neighbor]) {
                    distances[neighbor] = newDistance;
                    queue.offer(new Node(neighbor, newDistance));
                }
            }       
        }
        
        // 최대 경로인 노드가 몇 개인지 탐색
        int maxDistance = 0;
        for (int i = 1 ; i <= n ; i++) {
            maxDistance = Math.max(maxDistance, distances[i]);
        }
        
        int count = 0;
        for (int distance : distances) {
            if (distance == maxDistance) {
                count++;
            }
        }
        return count;
    }
}