class Solution {
    public long solution(int n, int[] times) {
        long start = 0L;
        long end = 1_000_000_000L * n;
        
        long answer = 0L;
        while(start <= end) {
            long mid = (start + end) / 2;
            long availableNumber = currentAvailableNumber(times, mid);
            
            if (availableNumber >= n) {
                answer = mid; // 일단 가능한 경우이므로 저장
                end = mid - 1;
            } else { // 아예 불가능한 경우이므로, 범위를 뒤로 이동시켜서 다시 탐색
                start = mid + 1;
            }
        }
        return answer;
    }
    
    // 현재 시간 currentTime까지 처리 가능한 사람의 총합을 계산
    public long currentAvailableNumber(final int[] times, long currentTime) {
        long availableNumber = 0L;
        for (int time : times) {
            availableNumber += (currentTime / time);
        }
        return availableNumber;
    }
}