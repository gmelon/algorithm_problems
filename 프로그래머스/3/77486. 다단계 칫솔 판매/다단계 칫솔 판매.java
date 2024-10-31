import java.util.*;

class Solution {
    
    static class Seller {
        String name;
        int value;
        Seller parent;
        
        Seller(Seller parent, String name, int value) {
            this.parent = parent;
            this.name = name;
            this.value = value;
        }
        
        void giveToParent(int amount) {
            int cut = amount / 10;
            if (cut > 0 && parent != null) {
                // 부모로 전달
                this.parent.giveToParent(cut);
            }
            
            this.value += amount - cut;
        }
    }
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        // enroll -> 판매자
        // referral -> 판매자를 등록한 판매자 (부모)
        Map<String, Seller> sellers = new HashMap<>(); // 이름에 대응하는 판매자 객체의 주솟값을 저장하는 map
        
        // 그래프 구성
        Seller center = new Seller(null, "민호", 0);
        for(int i = 0 ; i < enroll.length ; i++) {
            if (referral[i].equals("-")) {
                // 민호 직속
                Seller current = new Seller(center, enroll[i], 0);
                
                sellers.put(enroll[i], current);
            } else {
                // 직속이 아닌 경우
                Seller parent = sellers.get(referral[i]);
                Seller current = new Seller(parent, enroll[i], 0);
                
                sellers.put(enroll[i], current);
            }
        }
        
        // 계산
        for(int i = 0 ; i < amount.length ; i++) {
            sellers.get(seller[i]).giveToParent(amount[i] * 100);
        }
        
        // 정답 출력
        int[] answer = new int[enroll.length];
        for(int i = 0 ; i < answer.length ; i++) {
            answer[i] = sellers.getOrDefault(enroll[i], new Seller(null, "", 0)).value;
        }
        
        return answer;
    }
}