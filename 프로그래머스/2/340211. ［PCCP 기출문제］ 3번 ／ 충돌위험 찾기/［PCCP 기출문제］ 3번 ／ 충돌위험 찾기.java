import java.util.*;

class Solution {
    class Position {
        int x, y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o) {
            Position p = (Position) o;
            return p.x == this.x && p.y == this.y;
        }
    }
    
    class Robot {
        int x, y; // current position
        Deque<Position> route;
        
        public Robot(int x, int y, Deque<Position> route) {
            this.x = x;
            this.y = y;
            this.route = route;
        }
        
        // map에 저장할 key hash 값 계산
        public int hash() {
            return this.x * 100 + this.y;
        }
        
        // route의 다음 목적지로 최단 거리 이동
        public boolean move() {
            if (route.isEmpty()) {
                return false;
            }
            
            Position next = route.peekFirst();
            
            // 현재 위치와의 차이 계산
            int dx = next.x - this.x;
            int dy = next.y - this.y;
            
            if (dx != 0) {
                // x 좌표 우선 이동
                boolean negative = dx < 0;
                this.x = this.x + (negative ? -1 : 1);
            } else {
                // dx가 0일 때만 y 좌표 이동
                boolean negative = dy < 0;
                this.y = this.y + (negative ? -1 : 1);    
            }
            
            // next에 도달했다면 move 완료 후 position 삭제
            if (this.x == next.x && this.y == next.y) {
                route.removeFirst();
            }
            
            return true;
        }
    }
    public int solution(int[][] points, int[][] routes) {
        List<Robot> robots = new ArrayList<>();
        Map<Integer, Integer> crashCount = new HashMap<>();
        // robots init
        for(int i = 0, robotSize = routes.length, pointSize = routes[0].length ; i < robotSize ; i++) {            
            // route deque 구성
            Deque<Position> route = new ArrayDeque<>();
            for(int j = 1 ; j < pointSize ; j++) {
                int pointIndex = routes[i][j] - 1;
                route.addLast(new Position(points[pointIndex][0] - 1, points[pointIndex][1] - 1));
            }
            
            // 처음(0초)에 최초 포인트에서 시작
            int pointIndex = routes[i][0] - 1;
            Robot current = new Robot(points[pointIndex][0] - 1, points[pointIndex][1] - 1, route);
            robots.add(current);
            
            
            // map에 표시
            crashCount.merge(current.hash(), 1, (v1, v2) -> v1 + v2);
        }
        
        int answer = 0; // 실제 충돌 횟수
        // 최초 위치에서의 충돌 계산
        for(Map.Entry<Integer, Integer> entry : crashCount.entrySet()) {
            if (entry.getValue() >= 2) {
                answer += 1;
            }
        }
        
        // 더 이상 robot이 이동하지 않을 때 까지 반복
        boolean isMoved = true;
        while(isMoved) {
            // 변수 초기화
            isMoved = false;
            crashCount.clear();
            
            for(Robot robot: robots) {
                // 이동
                boolean result = robot.move();
                
                if (result) {
                    isMoved = true;
                    crashCount.merge(robot.hash(), 1, (v1, v2) -> v1 + v2);
                }
            }
            
            // 이동 완료 후 충돌 횟수 계산
            for(Map.Entry<Integer, Integer> entry : crashCount.entrySet()) {
                if (entry.getValue() >= 2) {
                    answer += 1;
                }
            }
        }
        
        return answer;
    }
}