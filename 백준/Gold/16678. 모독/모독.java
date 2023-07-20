import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(sc.nextInt());
        }
        sc.close();

        // 풀이 시작
        Collections.sort(list);

        long hackCount = 0;
        // 먼저 가장 첫 숫자가 1이 될 때 까지 해킹
        if (list.get(0) > 1) {
            int temp = list.get(0);
            list.set(0, 1);
            hackCount += temp - 1;
        }

        // 그 다음부터는 나머지 숫자들과의 차이가 1이하가 되도록 해킹
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i + 1) > list.get(i) + 1) {
                int temp = list.get(i + 1);
                list.set(i + 1, list.get(i) + 1);
                hackCount += temp - list.get(i + 1);
            }
        }

        System.out.println(hackCount);
    }

}
