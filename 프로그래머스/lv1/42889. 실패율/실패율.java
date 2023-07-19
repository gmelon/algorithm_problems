import java.util.*;
import java.util.Map.*;

class Solution {
    public int[] solution(int N, int[] stages) {
        Map<Integer, Double> fail = new HashMap<>();
        for(int i = 1 ; i <= N ; i++) {
            fail.put(i, 0.0);
        }

        Arrays.sort(stages);

        // 실패율 계산
        int playerCount = 0;
        int curStagePlayerCount = 0;
        for(int i = stages.length - 1 ; i >= 0 ; i--) {
            playerCount++;
            if (stages[i] > N) {
                // 모든 스테이지를 클리어한 경우 플레이어 수만 증가
                continue;
            }

            if (i - 1 >= 0 && stages[i] == stages[i - 1]) {
                curStagePlayerCount++;
                continue;
            } else {
                curStagePlayerCount++;
                fail.put(stages[i], ((double) curStagePlayerCount) / playerCount);

                curStagePlayerCount = 0;
            }
        }

        return fail.entrySet().stream()
            .sorted(Entry.<Integer, Double>comparingByValue(Comparator.reverseOrder()).thenComparingInt(Entry::getKey))
            .mapToInt(Entry::getKey)
            .toArray();
    }
}