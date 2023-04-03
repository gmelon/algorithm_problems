import java.util.*;

class Solution {
    public int solution(int n, int[][] results) {
        // 경기 결과를 담을 graph 배열
        int[][] graph = new int[n + 1][n + 1];
        
        // 초기 값은 모두 최대값으로 설정
        for (int i = 0 ; i < n + 1 ; i++) {
            Arrays.fill(graph[i], (int) 1e9);
        }
        
        // 자기 자신으로 가는 값은 0으로 설정
        for (int i = 1 ; i < n + 1 ; i++) {
            graph[i][i] = 0;
        }
        
        // 초기 간선 설정
        // A -> B로 도달하면 A가 B를 이긴 것
        for (int[] result : results) {
            graph[result[0]][result[1]] = 1;
            graph[result[1]][result[0]] = -1;
        }
        
        // 플로이드 워셜 알고리즘 수행
        for (int i = 1 ; i < n + 1 ; i++) {
            for (int j = 1 ; j < n + 1 ; j++) {
                for (int k = 1 ; k < n + 1 ; k++) {
                    if (graph[i][k] == 1 && graph[k][j] == 1) {
                        graph[i][j] = 1;
                        graph[j][i] = -1;
                    }
                    if (graph[i][k] == -1 && graph[k][j] == -1) {
                        graph[i][j] = -1;
                        graph[j][i] = 1;
                    }
                }
            }
        }
        
        int count = 0;
        for (int i = 1 ; i < n + 1 ; i++) {
            boolean flag = true;
            for (int j = 1 ; j < n + 1; j++) {
                if (graph[i][j] == (int) 1e9 || graph[j][i] == (int) 1e9) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                count++;
            }
        }
        for (int[] g : graph) {
            System.out.println(Arrays.toString(g));
        }
        return count;
    }
}