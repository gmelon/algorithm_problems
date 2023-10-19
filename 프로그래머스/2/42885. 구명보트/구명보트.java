import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        Arrays.sort(people);
        
        int left = 0;
        int right = people.length - 1;
        int answer = 0;
        for( ; left <= right ; right--) {
            if (left != right && people[left] + people[right] <= limit) {
                left++;
            }
            answer += 1;
        }
        
        return answer;
    }
}