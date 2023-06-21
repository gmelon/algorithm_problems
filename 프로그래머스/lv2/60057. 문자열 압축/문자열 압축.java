class Solution {
    public int solution(String s) {
        int min = s.length(); // 정답
        
        // 전 문자열을 matchString에 값을 기록해둔다 (matchCount = 1로 초기화)
        // 현재 문자열이 matchString과 같다면, matchCount++
        // matchString과 현재 문자열이 다르게 되는 순간 (matchCount + matchString) 을 새로운 문자열에 추가 (matchCount == 1 이면 matchString 만 추가)
        
        // 순회 완료 후 완성된 문자열의 길이를 현재의 min과 비교

        for (int compSize = 1 ; compSize < s.length() ; compSize++) {
            String newString = "";
            String matchString = "";
            int matchCount = 1;
            
            // 문자열 compSize씩 자르면서 순회
            for (int i = 0 ; i < s.length(); i += compSize) {
                // compSize == 3, s.length() == 11
                // i 0, 3, 6, 9
                String currentString = s.substring(i, Math.min(i + compSize, s.length()));
                if (i == 0) {
                    // 첫번째는 무조건 matchString에 넣기
                    matchString = currentString;
                    continue;
                }
                
                if (currentString.equals(matchString)) {
                    // 기존 문자열과 같음 (계속해서 압축)
                    matchCount++;
                } else {
                    // 기존 문자열과 다름 (압축 완료)
                    if (matchCount == 1) {
                        newString += matchString;    
                    } else {
                        newString += (matchCount + matchString);
                    }  
                    // 초기화
                    matchString = currentString;
                    matchCount = 1;
                }
                                                   
            }
            
            
            // 마지막 문자열은 무조건 더해주어야 함 (matchCount == 1이 아니면 압축된 상태로, 1이면 그대로)
            if (matchCount == 1) {
                newString += matchString;    
            } else {
                newString += (matchCount + matchString);
            }
            
            min = Math.min(min, newString.length());
        }
        
        return min;
    }
}