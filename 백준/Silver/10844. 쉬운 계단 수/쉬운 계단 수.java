import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        // 풀이 시작
        Map<Integer, Long> map = new HashMap<>();
        for (int i = 1; i <= 9; i++) {
            map.put(i, 1L);
        }
        map.put(0, 0L);

        for (int i = 1; i < N; i++) {
            Map<Integer, Long> newMap = new HashMap<>();
            for (int j = 0; j <= 9; j++) {
                if (j == 0) {
                    newMap.put(j, map.get(1) % 1_000_000_000);
                } else if (j == 9) {
                    newMap.put(j, map.get(8) % 1_000_000_000);
                } else {
                    newMap.put(j, map.get(j - 1) % 1_000_000_000 + map.get(j + 1) % 1_000_000_000);
                }
            }
            map = newMap;
        }

        long answer = 0L;
        for (int i = 0; i <= 9; i++) {
            answer += map.get(i) % 1_000_000_000;
        }
        System.out.println(answer % 1_000_000_000);
    }

}
