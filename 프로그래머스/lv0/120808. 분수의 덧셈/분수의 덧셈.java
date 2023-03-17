class Solution {
    public int[] solution(int numer1, int denom1, int numer2, int denom2) {
        // numer => 분자
        // denom => 분모
        int numer = denom2 * numer1 + denom1 * numer2;
        int denom = denom1 * denom2;
        
        for (int i = (denom >= numer ? denom : numer) ; i >= 1 ; i--) {
            if (denom % i == 0 && numer % i == 0) {
                denom /= i;
                numer /= i;
            }
        }
        return new int[]{numer, denom};
    }
}