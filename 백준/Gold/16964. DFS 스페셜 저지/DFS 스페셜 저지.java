import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    // 방문해야 할 정점 후보가 아닌 다른 정점을 방문하게 되면 실패

    static int currentVisitIndex = 1; // 검증 대상 방문 순서 index
    static List<List<Integer>> graph;
    static int[] userAnswers;
    static int[] outDegrees;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        // 간선 입력
        outDegrees = new int[N + 1]; // 해당 정점에서 '나가는' 간선 개수
        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph.get(from).add(to);
            graph.get(to).add(from);

            outDegrees[from]++;
            outDegrees[to]++;
        }

        // 이분탐색을 위해 간선을 정렬
        for (int i = 0; i <= N; i++) {
            Collections.sort(graph.get(i));
        }

        visited = new boolean[N + 1];
        userAnswers = new int[N]; // 검증해야 할 방문 순서
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            userAnswers[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // 풀이 시작
        System.out.println(dfs(1) ? 1 : 0);
    }

    static boolean dfs(int currentIndex) {
        visited[currentIndex] = true;

        test: while (outDegrees[currentIndex] > 0) {
            // 현재 방문 가능한 정점에 다음 방문한 노드가 있는지 확인
            int left = 0;
            int right = graph.get(currentIndex).size() - 1;

            while(left <= right) {
                int candidateIndex = (left + right) / 2;
                int candidate = graph.get(currentIndex).get(candidateIndex);

                if (candidate == userAnswers[currentVisitIndex] && !visited[candidate]) {
                    currentVisitIndex++;

                    // 방문 처리
                    visited[candidate] = true;

                    // 차수 갱신
                    outDegrees[currentIndex]--;
                    outDegrees[candidate]--;

                    // 다음 레벨로 진행
                    if (!dfs(candidate)) {
                        return false;
                    }
                    continue test;
                }

                if (candidate < userAnswers[currentVisitIndex]) {
                    left = candidateIndex + 1;
                } else {
                    right = candidateIndex - 1;
                }
            }

            // dfs로 처리되어야할 이웃노드가 모두 처리되지 않고 내려옴, 불가능한 경우
            return false;
        }

        return true; // 현재 레벨에서 모든 방문 순서를 만족함
    }

}