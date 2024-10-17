import java.util.*;

class Solution {

    int[] selected;
    int minValue = Integer.MAX_VALUE;

    int K, N;
    int[][][] requests;

    public int solution(int k, int n, int[][] reqs) {
        // 0. 초기화
        selected = new int[k]; // 각 유형별 멘토 수
        Arrays.fill(selected, 1); // 최소 1명씩은 배정
        K = k;
        N = n;

        // reqs를 상담 유형별로 분리
        int[] count = new int[k];
        for (int[] req : reqs) {
            count[req[2] - 1]++;
        }

        requests = new int[k][][];
        for (int i = 0; i < k; i++) {
            requests[i] = new int[count[i]][3];
        }

        int[] curIndex = new int[k];
        for (int[] req : reqs) {
            int type = req[2] - 1; // 유형 1부터 시작하므로 -1
            requests[type][curIndex[type]++] = req;
        }

        // 중복 조합 계산 및 최솟값 탐색
        distributeMentors(0, n - k);
        
        return minValue;
    }

    // 중복 조합으로 멘토를 분배하는 함수
    void distributeMentors(int count, int remaining) {
        if (count == selected.length) {
            if (remaining == 0) {
                minValue = Math.min(minValue, calculateWaitTime());
            }
            return;
        }

        for (int i = 0; i <= remaining; i++) {
            selected[count] += i;
            distributeMentors(count + 1, remaining - i);
            selected[count] -= i;
        }
    }

    // 대기 시간 계산
    int calculateWaitTime() {
        int totalWaitTime = 0;

        for (int i = 0; i < K; i++) {
            int[] mentors = new int[selected[i]];
            PriorityQueue<Integer> pq = new PriorityQueue<>();

            for (int[] req : requests[i]) {
                int startTime = req[0];
                int duration = req[1];

                // 현재 시간보다 먼저 끝난 상담은 종료
                while (!pq.isEmpty() && pq.peek() <= startTime) {
                    pq.poll();
                    mentors[pq.size()]++;
                }

                // 멘토가 있으면 바로 상담
                if (pq.size() < selected[i]) {
                    pq.offer(startTime + duration);
                } else {
                    // 멘토가 없으면 가장 빨리 끝나는 시간까지 대기
                    int endTime = pq.poll();
                    totalWaitTime += (endTime - startTime);
                    pq.offer(endTime + duration);
                }
            }
        }

        return totalWaitTime;
    }
}
