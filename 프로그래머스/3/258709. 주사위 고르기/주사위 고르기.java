import java.util.*;

class Solution {
    static int n;
    static int[] selected;
    
    static int[] answer;
    static int globalLowerCount = Integer.MIN_VALUE; // A 기준 자신보다 작은 수가 몇개 존재하는가
    
    static int[][] givenDice;
    
    public int[] solution(int[][] dice) {
        // n개의 주사위 중 n / 2개를 선택 (조합, 10C5)
        n = dice.length;
        givenDice = dice;
        selected = new int[n / 2];
        answer = new int[n / 2];
        comb(0, 0);
        
        for(int i = 0 ; i < n / 2 ; i++) {
            answer[i] = answer[i] + 1;
        }
        
        return answer;
    }
    
    public void comb(int current, int index) {
        if (current == n / 2) {
            // 선택되지 않은 리스트 구성
            int[] unselected = new int[n / 2];
            for(int i = 0, selectedIndex = 0, unselectedIndex = 0 ; i < n ; i++) {
                if (selectedIndex >= n / 2) {
                    unselected[unselectedIndex] = i;
                    unselectedIndex++;
                    continue;
                }
                
                if (selected[selectedIndex] != i) {
                    unselected[unselectedIndex] = i;
                    unselectedIndex++;
                } else {
                    selectedIndex++;
                }
            }
            
            // 선택된 양쪽 n / 2 개의 주사위의 모든 경우의 수를 구해 합을 계산 (6^5 * 2)
            List<Integer> aList = new ArrayList<>();
            calcSums(aList, selected, 0, 0);
            
            List<Integer> bList = new ArrayList<>();
            calcSums(bList, unselected, 0, 0);
        
            // 양쪽의 합을 정렬한 뒤,
            aList.sort((i1, i2) -> i1 - i2);
            bList.sort((i1, i2) -> i1 - i2);
            
            // 이분탐색으로 A 기준 B에 자신보다 작은 수가 몇개 존재하는지 확인. 이 값이 가장 클 때 선택된 주사위가 정답
            int lowerCount = 0;
            for(int a : aList) {
                int left = 0;
                int right = bList.size() - 1;
                int target = -1;
                while(left <= right) {
                    int mid = (left + right) / 2;
                    
                    if (a <= bList.get(mid)) {
                        target = mid;
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                
                if (target == -1) {
                    lowerCount += bList.size();
                    continue;
                }
                
                if (a == target) {
                    // target -= 1;
                }
                lowerCount += target;
            }
            
            if (globalLowerCount < lowerCount) {
                globalLowerCount = lowerCount;
                answer = selected.clone();
            }
            
            return;
        }
        
        for(int i = index ; i < n ; i++) {
            selected[current] = i;
            comb(current + 1, i + 1);
        }
    }
    
    public void calcSums(List<Integer> result, int[] given, int depth, int sum) {
        if (depth == given.length) {
            // 한가지 경우의 수 계산 완료
            result.add(sum);
            return;
        }
        
        for(int i = 0 ; i < 6 ; i++) {
            calcSums(result, given, depth + 1, sum + givenDice[given[depth]][i]);
        }
    }
}