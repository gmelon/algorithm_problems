class Solution {
    
    public int solution(int[][] board, int[][] skill) {
        int N = board.length;
        int M = board[0].length;
        
        int[][] acc = new int[N + 1][M + 1];
        for (int[] s: skill) {
            int type = s[0];
            int r1 = s[1];
            int c1 = s[2];
            int r2 = s[3];
            int c2 = s[4];
            int degree = s[5];
            
            if (type == 1) {
                degree = -degree;
            }
            
            // 누적합 배열 생성
            acc[r1][c1] += degree;
            acc[r2 + 1][c1] -= degree;
            acc[r1][c2 + 1] -= degree;
            acc[r2 + 1][c2 + 1] += degree;
        }
        
        // 세로 방향 누적합
        for(int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < M ; j++) {
                acc[i + 1][j] += acc[i][j];
            }
        }
        
        // 가로 방향 누적합
        for(int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < M ; j++) {
                acc[i][j + 1] += acc[i][j];
            }
        }
        
        // 최종적으로 board에 누적합 적용
        int count = 0;
        for(int i = 0 ; i < N ; i++) {
            for(int j = 0 ; j < M ; j++) {
                board[i][j] += acc[i][j];
                if (board[i][j] >= 1) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
