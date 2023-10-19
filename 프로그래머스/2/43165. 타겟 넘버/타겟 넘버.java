class Solution {
    
    public int solution(int[] numbers, int target) {
        boolean[] visited = new boolean[numbers.length];
        
        return dfs(0, -1, numbers, target);
    }
    
    int dfs(int currentValue, int currentIndex, int[] numbers, int target) {
        if (currentIndex >= numbers.length - 1) {
            if(currentValue == target) {
                return 1;
            }
            return 0;
        }
        
        int count = 0;
        int nextIndex = currentIndex + 1;
        
        count += dfs(currentValue + numbers[nextIndex], nextIndex, numbers, target);
        count += dfs(currentValue - numbers[nextIndex], nextIndex, numbers, target);
        
        return count;
    }
    
}