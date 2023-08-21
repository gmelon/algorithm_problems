import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        List<List<Integer>> triangle = new ArrayList<>();
        int count = 1;
        for (int i = 0; i < n; i++) {
            triangle.add(new ArrayList<>());
            for (int j = 1; j <= count; j++) {
                triangle.get(i).add(sc.nextInt());
            }
            count++;
        }

        sc.close();

        // 풀이 시작
        for (int i = 1; i < n; i++) {
            int currentLevelLength = triangle.get(i).size();
            for (int j = 0; j < currentLevelLength; j++) {
                int topLeft = 0;
                int top = 0;
                if (j - 1 >= 0) {
                    topLeft = triangle.get(i - 1).get(j - 1);
                }
                if (j < triangle.get(i - 1).size()) {
                    top = triangle.get(i - 1).get(j);
                }

                triangle.get(i).set(j, triangle.get(i).get(j) + Math.max(top, topLeft));
            }
        }

        // 마지막 행 순회
        int answer = 0;
        for (int i = 0; i < n; i++) {
            answer = Math.max(answer, triangle.get(n - 1).get(i));
        }
        System.out.println(answer);
    }

}
