import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    static class Condition implements Comparable<Condition> {
        int before;
        int after;

        public Condition(int before, int after) {
            this.before = before;
            this.after = after;
        }

        @Override
        public int compareTo(Condition o) {
            return this.before - o.before;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 과목의 수
        int M = sc.nextInt(); // 선수 조건 수

        List<Condition> conditions = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            conditions.add(new Condition(sc.nextInt(), sc.nextInt()));
        }
        sc.close();

        // 풀이 시작
        // 선수 과목 기준 오름차순 정렬
        Collections.sort(conditions);

        int[] dp = new int[N + 1];
        Arrays.fill(dp, 1); // 모두 기본으로 1학기 소요

        for (Condition condition : conditions) {
            dp[condition.after] = Math.max(dp[condition.before] + 1, dp[condition.after]);
        }

        String answer = IntStream.rangeClosed(1, N)
            .mapToObj(i -> String.valueOf(dp[i]))
            .collect(Collectors.joining(" "));
        System.out.println(answer);
    }

}
