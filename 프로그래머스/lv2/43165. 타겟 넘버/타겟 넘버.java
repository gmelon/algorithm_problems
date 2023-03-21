class Solution {
    
    static int answer = 0;
    
    public int solution(int[] numbers, int target) {
        dfs(numbers, 1, numbers[0], target);
        dfs(numbers, 1, -numbers[0], target);
        return answer;
    }
    
    public void dfs(final int[] numbers, int index, int sum, int target) {
        if (index == numbers.length) {
            if (sum == target) {
                answer++;
            }
            return;
        }
        dfs(numbers, index + 1, sum + numbers[index], target);
        dfs(numbers, index + 1, sum - numbers[index], target);
    }
}