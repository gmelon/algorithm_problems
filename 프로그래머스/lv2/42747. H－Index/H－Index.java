import java.util.Arrays;

class Solution {
    public int solution(int[] citations) {
        // 정렬
        Arrays.sort(citations);
        
        // h번 이상 인용된 논문이 h편 이상이어야 하므로
        // 즉, 인용 횟수와 인용된 논문 개수가 동시에 h이상 일 때 h의 최대 값을 구하는 문제
        
        // 정렬된 상태에서 citations[i] >= citations.length - i 가 되는 순간에
        // (h = citations.length - i) 의 최대 값이 나오게 됨
        for (int i = 0 ; i < citations.length ; i++) {
            if (citations[i] >= citations.length - i) {
                return citations.length - i;
            }
        }
        return 0;
    }
}