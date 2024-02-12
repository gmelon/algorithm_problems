import java.util.*;

class Solution {
    static class Feature {
        int progress, speed;
        Feature(int progress, int speed) {
            this.progress = progress;
            this.speed = speed;
        }
    }
    
    public int[] solution(int[] progresses, int[] speeds) {
        Deque<Feature> queue = new ArrayDeque<>();
        for(int i = 0 ; i < progresses.length ; i++) {
            queue.offer(new Feature(progresses[i], speeds[i]));
        }
        
        List<Integer> answers = new ArrayList<>();
        while(!queue.isEmpty()) {
            // 작업 진행
            for(Feature feature : queue) {
                feature.progress += feature.speed;
            }
            
            // 출시할 수 있는 기능들 한번에 출시
            int count = 0;
            while(!queue.isEmpty() && queue.peek().progress >= 100) {
                queue.poll();
                count++;
            }
            if (count != 0) {
                answers.add(count);   
            }
        }
        
        return answers.stream().mapToInt(i -> i).toArray();
    }
}