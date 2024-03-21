import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        while (true) {
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            if (from == -1) {
                break;
            }

            graph.get(from).add(to);
            graph.get(to).add(from);

            st = new StringTokenizer(br.readLine());
        }

        // 회원의 점수, 모든 다른 회원과의 거리 중 최대값 구하기
        int[] scores = new int[N + 1];
        int minScore = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i++) {
            boolean[] visited = new boolean[N + 1];
            Queue<Integer> queue = new ArrayDeque<>();

            // init
            visited[i] = true;
            queue.offer(i);

            int score = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                score++;
                while (size-- > 0) {
                    int current = queue.poll();

                    // 이웃 방문
                    for (int neighbor : graph.get(current)) {
                        if (visited[neighbor]) {
                            continue;
                        }

                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            score -= 1; // 마지막 친구들 뺴주기
            scores[i] = score;
            minScore = Math.min(minScore, score);
        }

        // 답 찾기
        List<Integer> answers = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (scores[i] == minScore) {
                answers.add(i);
            }
        }

        // 답 출력
        Collections.sort(answers);
        System.out.println(minScore + " " + answers.size());
        for (int answer : answers) {
            System.out.print(answer + " ");
        }
    }
}