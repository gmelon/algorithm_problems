class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int[][] answer = new int[m + 1][n + 1];
        
        // 물에 잠긴 지역 처리
        for (int i = 0 ; i < puddles.length ; i++) {
            answer[puddles[i][0]][puddles[i][1]] = -1;
        }
        
        // 초기 좌표 값 설정
        answer[1][1] = 1;
        
        // 순회하며 경우의 수 계산
        for (int x = 1 ; x <= m ; x++) {
            for (int y = 1 ; y <= n ; y++) {
                // 물에 잠긴 지역 제외
                if (answer[x][y] == -1) {
                    answer[x][y] = 0;
                    continue;
                }
                
                // 나머지 정상 경로 계산
                if (x != 1) {
                    answer[x][y] += answer[x - 1][y] % 1_000_000_007;
                }
                if (y != 1) {
                    answer[x][y] += answer[x][y - 1] % 1_000_000_007;
                }
            }
        }
        return answer[m][n] % 1_000_000_007;
    }
}