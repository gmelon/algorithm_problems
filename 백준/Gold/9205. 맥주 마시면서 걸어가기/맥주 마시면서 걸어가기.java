import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean available(int x, int y) {
            // 맥주 20명을 마시면서 이동할 수 있는 거리인가?
            return Math.abs(this.x - x) + Math.abs(this.y - y) <= 1_000;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        test: for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine()); // 편의점의 개수

            // graph init
            // 0번 - start, (length - 1)번 - end
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n + 2; i++) {
                graph.add(new ArrayList<>());
            }

            // graph 입력
            Position[] positions = new Position[n + 2];
            for (int i = 0; i < n + 2; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                positions[i] = new Position(x, y);

                // 이전까지 입력된 지점들을 순회하며 이동 가능한지 확인
                // 넣으면서 확인하기 때문에 중복되지 않음
                for (int j = 0; j < i; j++) {
                    if (positions[j].available(x, y)) {
                        // 가능, 그래프에 추가
                        graph.get(i).add(j);
                        graph.get(j).add(i);
                    }
                }
            }

            // bfs
            boolean[] visited = new boolean[n + 2];
            Queue<Integer> queue = new ArrayDeque<>();

            visited[0] = true;
            queue.offer(0); // 시작점은 0번 index

            while (!queue.isEmpty()) {
                int current = queue.poll();

                for (int neighbor : graph.get(current)) {
                    if (visited[neighbor]) {
                        continue;
                    }

                    if (neighbor == n + 1) {
                        // 페스티벌 장소에 도착
                        sb.append("happy").append("\n");
                        continue test;
                    }

                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }

            sb.append("sad").append("\n");
        }

        System.out.println(sb);
    }
}