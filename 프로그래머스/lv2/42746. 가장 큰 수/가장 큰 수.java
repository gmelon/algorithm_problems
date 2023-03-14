import java.util.*;
import java.util.stream.*;

class Solution {
    public String solution(int[] numbers) {        
        // 비교 및 sort를 위해 String으로 변환
        String[] list = Arrays.stream(numbers)
                                    .mapToObj(String::valueOf)
                                    .toArray(String[]::new);
        
        Arrays.sort(list, (o1, o2) -> Integer.valueOf(o2 + o1).compareTo(Integer.valueOf(o1 + o2)));
        
        // joining, return
        String result = Arrays.stream(list).collect(Collectors.joining(""));
        if (result.charAt(0) == '0') {
            return "0";
        }
        return result;
    }
}