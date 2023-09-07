import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static final int INF = 1_000_000_000;

    static class TestCase {

        int n;
        int[][] graph;

        public TestCase(int n) {
            this.n = n;
            this.graph = new int[n][n];
        }
    }

    static class Node implements Comparable<Node> {

        int x;
        int y;
        int value;

        public Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            return this.value - o.value;
        }
    }

    public static void main(String[] args) {
        List<TestCase> testCases = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        while (n != 0) {
            TestCase testCase = new TestCase(n);
            for (int i = 0; i < testCase.n; i++) {
                for (int j = 0; j < testCase.n; j++) {
                    testCase.graph[i][j] = sc.nextInt();
                }
            }
            testCases.add(testCase);
            n = sc.nextInt();
        }
        sc.close();

        // 풀이 시작
        for (int i = 0; i < testCases.size(); i++) {
            TestCase testCase = testCases.get(i);
            System.out.println("Problem " + (i + 1) + ": " + solution(testCase.graph));
        }

    }

    public static int solution(int[][] graph) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[][] values = new int[graph.length][graph.length];
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                values[i][j] = INF;
            }
        }

        // 초기값 설정
        values[0][0] = graph[0][0];
        pq.offer(new Node(0, 0, graph[0][0]));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.value > values[current.x][current.y]) {
                continue;
            }

            int[] dx = {1, -1, 0, 0};
            int[] dy = {0, 0, 1, -1};
            for (int i = 0; i < dx.length; i++) {
                int nextX = current.x + dx[i];
                int nextY = current.y + dy[i];

                if (nextX < 0 || nextX >= values.length || nextY < 0 || nextY >= values.length) {
                    continue;
                }

                if (values[nextX][nextY] > values[current.x][current.y] + graph[nextX][nextY]) {
                    pq.offer(new Node(nextX, nextY, values[current.x][current.y] + graph[nextX][nextY]));
                    values[nextX][nextY] = values[current.x][current.y] + graph[nextX][nextY];
                }
            }
        }

        return values[graph.length - 1][graph.length - 1];
    }

}
