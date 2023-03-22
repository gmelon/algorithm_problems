import java.util.*;

class Solution {
    static class Location {
        int x, y;
        
        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        Location left() {
            return new Location(x - 1, y);
        }
        
        Location right() {
            return new Location(x + 1, y);
        }
        
        Location up() {
            return new Location(x, y - 1);
        }
        
        Location down() {
            return new Location(x, y + 1);
        }
    }
    
    class Position {
        Location location;
        int steps;
        
        Position(Location location, int steps) {
            this.location = location;
            this.steps = steps;
        }
    }
    
    public int solution(int[][] maps) {
        int maxX = maps.length;
        int maxY = maps[0].length;
        
        boolean[][] visited = new boolean[maxX][maxY];
        Location target = new Location(maxX - 1, maxY - 1);
        
        Queue<Position> queue = new LinkedList<>();
        queue.offer(new Position(new Location(0, 0), 1));
        while(!queue.isEmpty()) {
            Position current = queue.poll();
            
            // 유효한 좌표인지 확인
            if (current.location.x < 0 || current.location.x >= maxX
               || current.location.y < 0 || current.location.y >= maxY 
               || visited[current.location.x][current.location.y]
               || maps[current.location.x][current.location.y] == 0) {
                continue;
            }
            
            // 유효한 좌표라면 방문 체크
            visited[current.location.x][current.location.y] = true;
            
            // 타겟 좌표인지 확인
            if (current.location.x == target.x && current.location.y == target.y) {
                return current.steps;
            }
            
            // 다음 좌표로 이동
            queue.offer(new Position(current.location.left(), current.steps + 1));
            queue.offer(new Position(current.location.right(), current.steps + 1));
            queue.offer(new Position(current.location.up(), current.steps + 1));
            queue.offer(new Position(current.location.down(), current.steps + 1));
        }
        return -1;
    }
}