import java.util.*;

class Solution {
    public int solution(int N, int number) {
        // number == N 인 경우 1개만 사용해도 가능
        if (number == N) {
            return 1;
        }
        List<Integer>[] dp = new ArrayList[9];
        
        // 사용횟수가 1일 때
        dp[1] = new ArrayList<>();
        dp[1].add(N);
        
        // 사용횟수가 2 ~ 8까지 일 때
        for (int i = 2 ; i <= 8 ; i++) {
            dp[i] = new ArrayList<>();
            // 단순 N이 i번개 있는 경우
            int first = 0;
            for (int j = 0 ; j < i ; j++) {
                first = first * 10 + N;
            }
            dp[i].add(first);
            
            // 합해서 현재 사용횟수가 되는 경우의 수들을 조합
            for (int j = 1 ; j <= i - 1 ; j++) {
                // dp[j] 와 dp[i-j]의 경우의 수를 조합해서 현재의 경우의 수 dp[i]를 생성
                for (Integer x : dp[j]) {
                    for (Integer y: dp[i-j]) {
                        if (x == 0 || y == 0) {
                            continue;
                        }
                        dp[i].add(x + y);
                        dp[i].add(x - y);
                        dp[i].add(x * y);
                        dp[i].add(x / y);
                    }
                }
            }
            
            // number가 완성되는지 확인
            if (dp[i].contains(number)) {
                return i;
            }
        }
        
        return -1;
    }
}