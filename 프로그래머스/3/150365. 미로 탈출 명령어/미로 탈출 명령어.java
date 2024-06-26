import java.util.*;

class Solution {
    
    static class Position {
        int x, y;
        String route;
        
        Position(int x, int y, String route) {
            this.x = x;
            this.y = y;
            this.route = route;
        }
        
        // 두 개의 Position을 비교하여 더 문자열이 작은 친구만 살린다
        public void merge(Position other) {
            if (this.route.compareTo(other.route) > 0) {
                this.route = other.route;
            }
        }
        
    }
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        Position start = new Position(x - 1, y - 1, "");
        
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        char[] routes = {'d', 'u', 'r', 'l'};
        
        Position[][] map = new Position[n][m];
        Queue<Position> queue = new ArrayDeque<>();
        
        // init
        queue.offer(start);
        map[start.x][start.y] = start;
        
        int curK = 0;
        while(curK < k && !queue.isEmpty()) {
            int size = queue.size();
            curK++;
            
            while(size-- > 0) {
                Position current = queue.poll();
                
                for(int d = 0 ; d < 4 ; d++) {
                    int nX = current.x + dx[d];
                    int nY = current.y + dy[d];
                    
                    if (nX < 0 || nX >= n || nY < 0 || nY >= m) {
                        continue;
                    }
                    
                    Position next = new Position(nX, nY, current.route + routes[d]);
                    if (map[nX][nY] == null || map[nX][nY].route.length() < current.route.length()) {
                        map[nX][nY] = next;
                        queue.offer(next);
                    } else {
                        // 이미 존재한다면 merge (더 route가 작은 것만 남기기)
                        // 큐에 이미 해당 객체가 offer 되어 있으므로 넣어줄 필요 X
                        map[nX][nY].merge(next);   
                    }
                }
            }
        }
        
        if (map[r - 1][c - 1] == null || map[r - 1][c - 1].route.length() < k) {
            return "impossible";
        } else {
            return map[r - 1][c - 1].route;
        }
    }
}