import java.util.*;

class Solution {
    public int solution(int n, int[][] computers) {
        boolean[] visited = new boolean[n];
        int networkCount = 0;
        
        for (int i = 0 ; i < n ; i++) {
            if (visited[i]) {
                continue;
            }
            networkCount++;
            visitAllComputersInNetwork(computers, visited, i);
        }
        
        return networkCount;
    }
    
    public void visitAllComputersInNetwork(final int[][] computers, boolean[] visited, int startIndex) {
        Stack<Integer> stack = new Stack<>();
        stack.push(startIndex);
        
        while(!stack.isEmpty()) {
            int currentIndex = stack.pop();
            visited[currentIndex] = true;
            
            // 인접 노드 stack에 추가
            for (int i = 0 ; i < computers[currentIndex].length ; i++) {
                if (!visited[i] && !stack.contains(i) && computers[currentIndex][i] == 1) {
                    stack.push(i);   
                }
            }
        }
    }
}