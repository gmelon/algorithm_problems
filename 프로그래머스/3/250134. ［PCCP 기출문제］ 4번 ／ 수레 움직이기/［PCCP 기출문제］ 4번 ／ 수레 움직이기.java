class Solution {
    static class Position {
        int x, y;
        
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object rawOther) {
            Position other = (Position) rawOther;
            return this.x == other.x && this.y == other.y;
        }
        
        public String toString() {
            return "Position(x: " + x + ", y:" + y + ")";
        }
    }
    
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    
    static boolean[][] redVisited, blueVisited;
    static Position redGoal, blueGoal;
    
    static int N, M;
    
    static int minCount = 1_000_000; // 필요한 턴의 최솟값
    
    public int solution(int[][] maze) {
        Position red = null, blue = null;
        
        N = maze.length;
        M = maze[0].length;
        
        redVisited = new boolean[N][M];       
        blueVisited = new boolean[N][M];
        
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                if (maze[i][j] == 1) {
                    red = new Position(i, j);
                    redVisited[red.x][red.y] = true;
                }
                if (maze[i][j] == 2) {
                    blue = new Position(i, j);
                    blueVisited[blue.x][blue.y] = true;
                }
                if (maze[i][j] == 3) {
                    redGoal = new Position(i, j);
                }
                if (maze[i][j] == 4) {
                    blueGoal = new Position(i, j);
                }
                if (maze[i][j] == 5) {
                    // 벽인 경우
                    redVisited[i][j] = true;
                    blueVisited[i][j] = true;
                }
            }
        }
        
        // 모든 경우 시도해보기
        
        
        tryAll(red, blue, 0);
        
        if (minCount == 1_000_000) return 0;
        return minCount;
    }
    
    void tryAll(Position currentRed, Position currentBlue, int count) {
        // 목표에 도달한 경우 멈춤
        if (currentRed.equals(redGoal) && currentBlue.equals(blueGoal)) {
            minCount = Math.min(minCount, count);
            return;
        }
            
        // 먼저 빨강을 움직임
        for(int redD = 0 ; redD < 4 ; redD++) {
            int redNextX = currentRed.x + dx[redD];
            int redNextY = currentRed.y + dy[redD];
            
            // 목표에 이미 도달했다면 움직이지 않는다
            Position nextRed = new Position(redNextX,redNextY);
            if (currentRed.equals(redGoal)) {
                nextRed = currentRed;
            } else {
                if (!isRangeValid(redNextX, redNextY) || redVisited[redNextX][redNextY]) {
                    continue;
                }
                
                // (이동하지 않을) 파랑이 있는 곳으로는 이동 불가
                if (currentBlue.equals(blueGoal) && nextRed.equals(currentBlue)) {
                    continue;
                }
            }
            
            redVisited[nextRed.x][nextRed.y] = true;
            
            // 다음으로 파랑 움직이기
            for(int blueD = 0 ; blueD < 4 ; blueD++) {
                int blueNextX = currentBlue.x + dx[blueD];
                int blueNextY = currentBlue.y + dy[blueD];
                
                Position nextBlue = new Position(blueNextX,blueNextY);
                
                // 목표에 이미 도달했다면 움직이지 않는다
                if (currentBlue.equals(blueGoal)) {
                    nextBlue = currentBlue;
                } else {
                    if (!isRangeValid(blueNextX, blueNextY) || blueVisited[blueNextX][blueNextY]) {
                        continue;
                    }

                    // 빨강과 동일한 지점으로는 이동 불가
                    if (nextRed.equals(nextBlue)) {
                        continue;
                    }

                    // 서로가 서로의 자리로는 이동 불가
                    if (nextRed.equals(currentBlue) && nextBlue.equals(currentRed)) {
                        continue;
                    }

                    // !!이동 가능!!    
                }
                
                blueVisited[nextBlue.x][nextBlue.y] = true;
                
                // 재귀 호출
                tryAll(nextRed, nextBlue, count + 1);
                
                blueVisited[nextBlue.x][nextBlue.y] = false;
            }
            
            redVisited[nextRed.x][nextRed.y] = false;
        }
    }
    
    static boolean isRangeValid(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}