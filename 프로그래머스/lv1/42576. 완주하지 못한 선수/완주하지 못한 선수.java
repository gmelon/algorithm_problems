import java.util.*;

class Solution {
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> map = new HashMap<>();
        
        for (String p : participant) {
            Integer value = map.getOrDefault(p, 0);
            map.put(p, ++value);
        }
        
        for (String c : completion) {
            Integer value = map.get(c);
            if (value == 1) {
                map.remove(c);
            } else {
                map.put(c, --value);
            }
        }
        
        for (String key : map.keySet()) {
            return key;
        }
        
        return null;
    }
}