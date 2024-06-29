class Solution {
    public int solution(int n, int[] tops) {
        int able, disable;
        if (tops[0] == 1) {
            // 첫번째 블록 top 존재 O 
            able = 3;
            disable = 1;
        } else {
            // 첫번째 블록 top 존재 X
            able = 2;
            disable = 1;
        }
    
        for(int i = 1 ; i < tops.length ; i++) {
            // able = 이전 사용 가능하면서 이번에 사용했고 다음 블록 사용 가능 + 이전 사용 가능했지만 이번에 사용하지않았고 다음 블록 사용 가능 + 이전 사용 불가능하여 이번에 사용하지않았고 다음 블록 사용 가능
            // disable = 이전 사용 가능하지만 이번에 사용하지 않았고 다음 블록 사용 불가능 + 이전 사용 가능불가능하여 이번에 사용하지않았고 다음 블록 사용 불가능
            int tempAble = able;
            int tempDisable = disable;
            if (tops[i] == 1) {
                // top 존재 O
                able = (tempAble * 1 + tempAble * 2 + tempDisable * 2) % 10_007;
                disable = (tempAble * 1 + tempDisable * 1) % 10_007;
            } else {
                // top 존재 X
                able = (tempAble * 1 + tempAble * 1 + tempDisable * 1) % 10_007;
                disable = (tempAble * 1 + tempDisable * 1) % 10_007;
            }
        }
        
        return (able + disable) % 10_007;
    }
}