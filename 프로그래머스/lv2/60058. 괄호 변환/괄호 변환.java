import java.util.*;

class Solution {
    public String solution(String p) {
        return recursive(p);
    }
    
    public String recursive(String p) {
        // 빈 문자열인 경우 그대로 반환
        if (p.length() == 0) {
            return p;
        }
        
        // 가장 작은 균형잡힌 문자열 u 만들기
        int count = 0;
        int index = 0;
        String u = "";
        String v = "";
        for (; index < p.length() ; index++) {
            char c = p.charAt(index);
            if (c == '(') {
                count++;
                u += c;
            } else {
                count--;
                u += c;
            }
            // 최소 값의 균형잡힌 문자열이 만들어진 시점
            if (count == 0) {
                index++;
                break;
            }
        }       
        v = p.substring(index);
        
        // u가 올바른 문자열이면
        if (checkCorrect(u)) {
            // v에 대해 다시 위 과정을 수행
            return u + recursive(v);
        } else {  // u가 올바른 문자열이 아니면
            String answer = "(";
            answer += recursive(v);
            answer += ")";
            u = u.substring(1, u.length() - 1); // 첫번째와 마지막 문자를 제거
            
            // 반대로 뒤집어서 더하기
            for(char c : u.toCharArray()) {
                if (c == '(') {
                    answer += ')';
                } else {
                    answer += '(';
                }
            }
            return answer;
        }
    }
    
    public boolean checkCorrect(String p) {
        Stack<Character> stack = new Stack<>();
        
        for(char c : p.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }
}