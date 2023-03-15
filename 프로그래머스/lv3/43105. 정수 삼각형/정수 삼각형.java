import java.util.Arrays;

class Solution {
    public int solution(int[][] triangle) {
        int maxLength = triangle[triangle.length - 1].length;
        int[][] dp = new int[maxLength][maxLength];
        
        // 경로의 최대 값 계산
        for(int x = 0 ; x < triangle.length ; x++) {
            for (int y = 0 ; y < triangle[x].length ; y++) {
                int maxAbove = 0;
                if (x != 0 && y == 0) {
                    maxAbove = dp[x - 1][0];
                }
                if (x != 0 && y != 0) {
                    maxAbove = Math.max(dp[x - 1][y], dp[x - 1][y - 1]);
                }
                dp[x][y] = triangle[x][y] + maxAbove;
            }
        }
        
        // 마지막 라인 중 최대 값 구하기
        int answer = Integer.MIN_VALUE;
        for (int i = 0 ; i < triangle[maxLength - 1].length ; i++) {
            answer = Math.max(answer, dp[maxLength - 1][i]);
        }
        
        return answer;
    }
}