import java.util.*;
import java.util.stream.*;

class Solution {
    
    static class Food implements Comparable<Food> {
        public int time = 0;
        
        public Food(int time) {
            this.time = time;
        }
        
        @Override
        public int compareTo(Food other) {
            return this.time - other.time; // 오름차순
        }
    }
    
    public int solution(int[] food_times, long k) {
        // food_times로 List 만들기
        List<Food> foods = Arrays.stream(food_times)
            .mapToObj(Food::new)
            .collect(Collectors.toList());
        
        // 동일한 객체 참조로 PriorityQueue 만들기
        PriorityQueue<Food> pq = new PriorityQueue<>(foods);
        
        int iterCount = 0;
        while (k >= 0 && pq.size() > 0) {
            iterCount++;
            if (k >= pq.size()) {
                // 현재 남은 음식보다 k가 크면 전체에서 1씩 뺴준다
                // 실제로 1씩 빼는 연산을 하게 되면 timeout이 나게 되므로
                // PriorityQueue를 사용해서 현재 값이 1인 Food를 제외해준다
                // 현재 반복번째수를 기록해서 값이 반복번째수와 동일한 원소의 값을 0으로 만들고 PQ에서 제거한다
                
                k -= pq.size();
                
                Iterator<Food> iterator = pq.iterator();
                while (iterator.hasNext()) {
                    Food current = iterator.next();
                    if (current.time <= iterCount) {
                        current.time = 0;
                        iterator.remove();
                    } else {
                        break;
                    }
                }   
            } else {
                // 남은 음식의 개수가 k보다 작으면 남은 음식 중에 k번째 음식을 반환한다
                // 이때 반환되는 음식의 index는 제외됐던 음식도 포함되어야 한다
                // 동일한 객체를 참조하는 List와 PriorityQueue를 만들어서
                // 반복적인 삭제 연산에는 PQ를 사용하고, 마지막 index 찾기에는 List를 사용
                
                if (pq.size() == 0) {
                    // 더 이상 섭취해야 할 음식이 없는 경우
                    return -1;
                }
                
                int index = -1;
                while (k >= 0) {
                    index++;
                    if (foods.get(index).time == 0) {
                        continue;
                    }
                    k--;
                }
                return index + 1;
            }
        }
        return -1;
    }
}