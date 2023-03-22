class Solution {
    
    static int answer = Integer.MAX_VALUE;
    
    public int solution(String begin, String target, String[] words) {
        // words가 target을 포함하지 않으면 변환 불가, 0 반환
        boolean transferable = false;
        for (String word : words) {
            if (word.equals(target)) {
                transferable = true;
            }
        }
        if (!transferable) {
            return 0;
        }
        
        // begin에서 한 번에 변환 가능한 단어를 루트로 dfs를 시작
        boolean[] visited = new boolean[words.length];
        for (int i = 0 ; i < words.length ; i++) {
            if(transferable(begin, words[i])) {
                dfs(words, visited, i, 1, target);
            }
        }
        return answer;
    }
    
    public void dfs(String[] words, boolean[] visited, int index, int depth, String target) {
        // target으로 변환이 완료되면, 해당 탐색 종료
        if (words[index].equals(target)) {
            // 최단 경로 갱신
            answer = Math.min(answer, depth);
        }
        
        // 현재 노드 방문 처리
        visited[index] = true;
        
        // 현재 노드에서 변환 가능한 단어를 다음 레벨로 사용
        for (int nextIndex = 0 ; nextIndex < words.length ; nextIndex++) {
            if(!visited[nextIndex] && transferable(words[index], words[nextIndex])) {
                dfs(words, visited, nextIndex, depth + 1, target);
            }
        }
        
        // 현재 노드 방문 처리 clear
        visited[index] = false;
    }
    
    // source에서 target으로의 변환이 가능한지 여부를 확인
    public boolean transferable(String source, String target) {
        int count = 0;
        for (int i = 0 ; i < source.length() ; i++) {
            if (source.charAt(i) != target.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }
}