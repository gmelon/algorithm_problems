class Solution {
    static int[][] tempBoard;
    
    public int[] solution(int rows, int columns, int[][] queries) {
        tempBoard = new int[rows][columns];
        int[] answer = new int[queries.length];
        
        // 행렬 초기화
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                tempBoard[i][j] = i * columns + j + 1;
            }
        }
        
        for(int i = 0 ; i < queries.length ; i++) {
            answer[i] = rotate(queries[i]);
        }
        
        return answer;
    }
    
    public int rotate(int[] query) {
        int r1 = query[0] - 1; 
        int c1 = query[1] - 1;
        int r2 = query[2] - 1;
        int c2 = query[3] - 1;
        
        int start = tempBoard[r1][c1]; // 시작점
        int min = start;
        
        for(int i = r1; i < r2; i++) {
            tempBoard[i][c1] = tempBoard[i + 1][c1];
            min = Math.min(tempBoard[i][c1], min);
        }
        
        for(int i = c1; i < c2; i++) {
            tempBoard[r2][i] = tempBoard[r2][i + 1];
            min = Math.min(tempBoard[r2][i], min);
        }
        
        for(int i = r2; i > r1; i--) {
            tempBoard[i][c2] = tempBoard[i - 1][c2];
            min = Math.min(tempBoard[i][c2], min);
        }
        
        for(int i = c2; i > c1; i--) {
            tempBoard[r1][i] = tempBoard[r1][i - 1];
            min = Math.min(tempBoard[r1][i], min);
        }
        
        tempBoard[r1][c1+1] = start;
        return min;
    }
    
}
