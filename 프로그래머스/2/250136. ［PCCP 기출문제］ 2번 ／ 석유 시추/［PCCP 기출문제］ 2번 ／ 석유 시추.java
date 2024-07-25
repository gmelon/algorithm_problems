import java.util.*;

class Solution {
    
    static int[] counts;
    
    public int solution(int[][] land) {
        // 0이면 빈땅, 1이면 석유
        
        int n = land.length; // 세로
        int m = land[0].length; // 가로
        
        // 먼저 석유 개수 파악
        counts = new int[m]; // 열별 석유량
        
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < m ; j++) {
                if (land[i][j] == 1) {
                    bfs(i, j, land);
                }
            }
        }
        
        int max = 0;
        for(int i = 0 ; i < m ; i++) {
            max = Math.max(max, counts[i]);
        }
        
        return max;
    }
    
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    
    void bfs(int x, int y, int[][] land) {
        Queue<int[]> queue = new ArrayDeque<>();
        
        // init
        queue.add(new int[]{x, y});
        int count = 1;
        land[x][y] = 0;
        
        Set<Integer> set = new HashSet<>();
        set.add(y);
        
        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            
            for(int d = 0 ; d < 4 ; d++) {
                int nX = current[0] + dx[d];
                int nY = current[1] + dy[d];
                
                if (nX < 0 || nX >= land.length || nY < 0 || nY >= land[0].length) {
                    continue;
                }
                
                if (land[nX][nY] == 1) {
                    count++;
                    land[nX][nY] = 0;
                    queue.offer(new int[]{nX, nY});
                    set.add(nY);
                }
            }
        }
        
        for(int i : set) {
            counts[i] += count;
        }
    }
}