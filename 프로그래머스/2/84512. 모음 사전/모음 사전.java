class Solution {
    
    final String[] words = {"A", "E", "I", "O", "U"};
    int count = -1;
    
    public int solution(String word) {
        
        dfs("", word);
        
        return count;
    }
    
    boolean dfs(String currentWord, String targetWord) {
        count++;
        
        if (currentWord.equals(targetWord)) {
            return true;
        }
        
        if (currentWord.length() >= 5) {
            return false;
        }
        
        for(String w : words) {
            boolean found = dfs(currentWord + w, targetWord);
            if (found) {
                return true;
            }
        }
        
        return false;
    }
}