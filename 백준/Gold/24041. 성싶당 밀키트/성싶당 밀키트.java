import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static class Ingredient {

        int speed;
        int length;

        public Ingredient(int speed, int length) {
            this.speed = speed;
            this.length = length;
        }

        long segyoon(long day) {
            return speed * Math.max(1, day - length);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        int N = Integer.parseInt(st.nextToken()); // 재료의 개수
        int G = Integer.parseInt(st.nextToken()); // 먹을 수 있는 세균 수의 합 상한
        int K = Integer.parseInt(st.nextToken()); // 최대로 뺄 수 있는 중요하지 않은 재료 수

        List<Ingredient> essentialIngredients = new ArrayList<>();
        List<Ingredient> nonessentialIngredients = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(bf.readLine());
            Ingredient ingredient = new Ingredient(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

            if (Integer.parseInt(st.nextToken()) == 1) {
                nonessentialIngredients.add(ingredient);
            } else {
                essentialIngredients.add(ingredient);
            }
        }
        bf.close();

        // 풀이 시작
        long start = 0;
        long end = 2_000_000_000;
        long answer = 0;
        while (start <= end) {
            // 밀키트를 산 날부터 mid일 후 (까지 먹을 수 있는지 확인)
            long mid = (start + end) / 2;

            // 세균 수를 기준으로 재료들을 정렬
            if (nonessentialIngredients.size() > K) {
                nonessentialIngredients.sort(Comparator.comparing((Ingredient i) -> i.segyoon(mid)).reversed());
            }

            // 최대 K개의 재료를 제외하고 총 세균 수를 카운트
            long count = 0;
            int currentK = 0;
            for (Ingredient nonessentialIngredient : nonessentialIngredients) {
                if (currentK < K) {
                    currentK++;
                    continue;
                }
                count += nonessentialIngredient.segyoon(mid);
            }
            for (Ingredient ingredient : essentialIngredients) {
                count += ingredient.segyoon(mid);
            }

            if (count <= G) {
                // 먹을 수 있다
                answer = mid;
                start = mid + 1;
            } else {
                // 먹을 수 없다
                end = mid - 1;
            }
        }
        System.out.println(answer);

    }

}
