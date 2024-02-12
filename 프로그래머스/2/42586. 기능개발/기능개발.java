import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        Deque<Integer> queue = new ArrayDeque<>();
        for(int i = 0 ; i < progresses.length ; i++) {
            queue.offer((int) Math.ceil((double) (100 - progresses[i]) / speeds[i]));
        }
        
        List<Integer> answers = new ArrayList<>();
        int last = 0;
        while(!queue.isEmpty()) {
            last = queue.peek();
            
            // 출시할 수 있는 기능들 한번에 출시
            int count = 0;
            while(!queue.isEmpty() && queue.peek() <= last) {
                queue.poll();
                count++;
            }
            answers.add(count);
        }
        
        return answers.stream().mapToInt(i -> i).toArray();
    }
}