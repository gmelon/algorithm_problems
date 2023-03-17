class Solution {
    public int solution(int[] money) {
        // 집들이 원 형태를 띄기 때문에 첫 집과 마지막 집은 서로 동시에 선택될 수 없다.
        // 따라서 처음과 마지막 집을 제외한 경우를 각각 구해서 그 중 최대 값을 취한다.
        return Math.max(dp(money, 0, money.length - 2), 
                        dp(money, 1, money.length - 1));
    }
    
    public int dp(int[] money, int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex >= money.length) {
            throw new IllegalArgumentException("유효하지 않은 index 입니다.");
        }
        
        int[] answer = new int[money.length];
        answer[startIndex] = money[startIndex];
        answer[startIndex + 1] = Math.max(money[startIndex], money[startIndex + 1]);
        
        for (int i = startIndex + 2 ; i <= endIndex ; i++) {
            // i번째까지의 집을 고려했을 때의 최대 값은 이전 최대 값을 취하거나
            // 이전이전의 최대 값에 현재 집의 값을 더한 값
            answer[i] = Math.max(answer[i - 2] + money[i], answer[i - 1]);
        }
        
        return answer[endIndex];
    }
}