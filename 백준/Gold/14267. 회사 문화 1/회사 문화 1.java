import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 직원 수
        int M = Integer.parseInt(st.nextToken()); // 칭찬 횟수

        List<List<Integer>> graph = new ArrayList<>();
        // graph init
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        // 그래프 구성
        st = new StringTokenizer(br.readLine());
        for (int to = 0; to < N; to++) {
            int from = Integer.parseInt(st.nextToken()) - 1;
            if (from == -2) {
                continue;
            }
            graph.get(from).add(to);
        }

        // 칭찬
        int[] prices = new int[N];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            prices[Integer.parseInt(st.nextToken()) - 1] += Integer.parseInt(st.nextToken());
        }

        // 계산
        Queue<Integer> queue = new ArrayDeque<>();

        // root init
        queue.offer(0);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for(int neighbor : graph.get(current)) {
                prices[neighbor] += prices[current];
                queue.offer(neighbor);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(prices[i]).append(" ");
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb);
    }
}