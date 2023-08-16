class Solution {
    public long solution(int n, int[] times) {
        // '심사를 받는데 걸리는 시간'을 조정하면서
        // 해당 값이 유효(해당 시간에 모든 사람이 심사를 받을 수 있음) 하면서 최소가 될 때를 찾는다
        
        long start = 0L;
        long end = 1_000_000_000L * 1_000_000_000L;
        long answer = 0L;
        
        while(start <= end) {
            long mid = (start + end) / 2;
            
            // 시간 mid 안에 모든 사람이 심사를 받을 수 있는지 판단
            long completedCount = 0L;
            for (int time : times) {
                completedCount += mid / time;
            }
            
            if (completedCount >= n) {
                // mid 안에 모든 사람 심사 가능
                // 시간 줄이기
                answer = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return answer;
    }
}