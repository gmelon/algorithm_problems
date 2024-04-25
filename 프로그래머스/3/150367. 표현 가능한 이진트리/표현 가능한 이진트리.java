class Solution {
    public int[] solution(long[] numbers) {
        int[] answers = new int[numbers.length];
        
        test: for(int i = 0 ; i < numbers.length ; i++) {
            String binary = toBinary(numbers[i]);
            
            // 먼저 가장 먼저 1이 나오는 index를 찾고,
            int firstIndex = 0;
            for(int j = 0 ; j < binary.length() ; j++) {
                if (binary.charAt(j) == '1') {
                    firstIndex = j;
                    break;
                }
            }
            
            // 해당 index부터 가능한 마지막 index까지 시도한다
            int lastIndex = (firstIndex + binary.length() - 1) / 2;
            for(int curIndex = firstIndex ; curIndex <= lastIndex ; curIndex++) {
                if (binary.charAt(curIndex) == '0') {
                    continue;
                }
                
                // 탐색 범위 지정
                int start = curIndex - (binary.length() - 1 - curIndex);
                int end = binary.length() - 1;
                
                // 범위가 홀수일 때만 가능
                if ((end - start + 1) % 2 == 0) {
                    continue;
                }
                
                if (check(start, end, binary, false)) {
                    answers[i] = 1;
                    continue test;      
                }
            }
        }
        
        return answers;
    }
    
    static String toBinary(long number) {
        String binary = Long.toBinaryString(number);
        
        // 맨 앞의 1이 가운데라고 했을 때 앞에 최대한의 0을 붙여준다
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < binary.length() - 1 ; i++) {
            sb.append("0");
        }
        
        sb.append(binary);
        return sb.toString();
    }
    
    static boolean check(int start, int end, String binary, boolean mustZero) {
        int mid = (start + end) / 2;
        
        if (mustZero && binary.charAt(mid) == '1') {
            return false;
        }
        
        // 위 조건을 통과하고, 같다면 종료
        if (start == end) {
            return true;
        }
        
        if (start > end) {
            return false;
        }
        
        if (binary.charAt(mid) == '0') {
            // 루트가 0이면 이하의 모든 자식들이 0이어야 함
            return check(start, mid - 1, binary, true) && check(mid + 1, end, binary, true);
        } else {
            // 루트가 1인 경우
            return check(start, mid - 1, binary, false) && check(mid + 1, end, binary, false);
        }
    }
}