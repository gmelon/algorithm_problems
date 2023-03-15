class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int[][] answer = new int[m + 1][n + 1];        
        
        // 물에 잠긴 지역 별도 배열로 생성
        int[][] processedPuddles = new int[m + 1][n + 1];
        for (int i = 0 ; i < puddles.length ; i++) {
            processedPuddles[puddles[i][0]][puddles[i][1]] = -1;
        }
        
        return search(m, n, m, n, answer, processedPuddles);
    }
    
    public int search(int m, int n,
                       int x, int y,
                       int[][] answer, int[][] processedPuddles) {
        // 초기 케이스
        if (x == 1 && y == 1) {
            return 1;
        }
        // 범위를 벗어나면 제외
        if (x > m || x < 1 || y > n || y < 1) {
            return 0;
        }
        // 물에 잠긴 지역은 제외
        if (processedPuddles[x][y] == -1) 
            return 0;
        
        // 메모이제이션
        if (answer[x][y] != 0) {
            return answer[x][y];
        }
        
        // 나머지 정상 케이스
        return answer[x][y] = (search(m, n, x - 1, y, answer, processedPuddles) 
                    + search(m, n, x, y - 1, answer, processedPuddles)) 
            % 1_000_000_007;
    }
}