import java.util.*;

class Solution {
    
    static class Food {
        long index;
        int leftTime;
        
        Food (long index, int leftTime) {
            this.index = index;
            this.leftTime = leftTime;
        }
    }
    
    public int solution(int[] food_times, long k) {
        PriorityQueue<Food> queue = new PriorityQueue<>((o1, o2) -> {
            // leftTime 기준 오름차순 정렬
            return o1.leftTime - o2.leftTime;  
        });
        
        for (int i = 0 ; i < food_times.length ; i++) {
            queue.offer(new Food(i + 1, food_times[i]));
        }
        
        long pre = 0;
        while (!queue.isEmpty()) {
            Food current = queue.peek();
            
            // 현재 빼야될 값의 합계
            long minusValue = (current.leftTime - pre) * queue.size();
            
            // 빼야될 값의 합계가 k보다 작다면,
            if (minusValue <= k) {
                // 빼기
                pre = queue.poll().leftTime;
                k -= minusValue;
                // 동일한 값이 있을 경우 같이 제외해준다
                while(!queue.isEmpty() && queue.peek().leftTime == current.leftTime) {
                    queue.poll();
                }
            }
            // 빼야될 값의 합계가 k보다 크다면,
            else if (minusValue > k) {
                // 인덱스로 다시 정렬한 후 k번째 값 찾기
                PriorityQueue<Food> indexQueue = new PriorityQueue<>((o1, o2) -> {
                    return (int) (o1.index - o2.index);
                });
                
                for (Food food : queue) {
                    indexQueue.offer(food);
                }
                
                long answer = 0;
                long iterSize = k % indexQueue.size();
                for (long i = 0 ; i <= iterSize ; i++) {
                    answer = indexQueue.poll().index;
                }
                return (int) answer;
            }
        }
        // k가 0으로 딱 떨어지는 경우
        return -1;
    }
    
}