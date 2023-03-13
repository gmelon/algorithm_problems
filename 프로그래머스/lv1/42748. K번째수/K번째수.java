import java.util.Arrays;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        
        for (int i = 0 ; i < commands.length ; i++) {
            // 주어지는 위치는 1부터 시작, 실제로는 -1을 뺴야함
            // 마지막 원소는 exclude이므로 -1 + 1 = 0 즉, 그대로 사용
            int[] slicedArray = Arrays.copyOfRange(array, commands[i][0] - 1, commands[i][1]);
            Arrays.sort(slicedArray);
            answer[i] = slicedArray[commands[i][2] - 1];
        }
        
        return answer;
    }
}