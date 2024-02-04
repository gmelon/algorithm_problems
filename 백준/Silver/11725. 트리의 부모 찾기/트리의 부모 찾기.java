import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int[] parents;
    static int N;
    static List<List<Integer>> tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        tree = new ArrayList<>();
        // graph init
        for (int i = 0; i < N + 1; i++) {
            tree.add(new ArrayList<>());
        }
        // graph input
        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            tree.get(from).add(to);
            tree.get(to).add(from);
        }
        br.close();

        // 풀이 시작
        parents = new int[N + 1];
        bfs();

        // print
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < N + 1; i++) {
            sb.append(parents[i]).append("\n");
        }
        System.out.println(sb);
    }

    static void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];

        // init
        queue.offer(1);
        visited[1] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int child : tree.get(current)) {
                if (visited[child]) {
                    continue;
                }

                parents[child] = current;
                visited[child] = true;
                queue.offer(child);
            }
        }
    }

}