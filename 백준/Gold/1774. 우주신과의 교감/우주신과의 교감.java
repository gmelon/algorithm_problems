import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static class Node {
        int index;
        int x, y;

        public Node(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }
    }

    static class NodeDistance {
        int from;
        int to;
        double distance;

        public NodeDistance(int from, int to, double distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = new Node(i + 1, sc.nextInt(), sc.nextInt());
        }

        int[] parents = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            parents[i] = i;
        }

        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            unionParent(from, to, parents);
        }

        sc.close();

        // 풀이 시작
        List<NodeDistance> distances = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    // 자기 자신으로의 거리는 계산 하지 않음
                    continue;
                }

                distances.add(new NodeDistance(nodes[i].index, nodes[j].index,
                    distance(nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y)));
            }
        }

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble((NodeDistance node) -> node.distance));
        pq.addAll(distances);

        double answer = 0;

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();

            if (findParent(current.from, parents) != findParent(current.to, parents)) {
                unionParent(current.from, current.to, parents);
                answer += current.distance;
            }
        }

        System.out.printf("%.2f", answer);

    }

    private static int findParent(int target, int[] parents) {
        if (target != parents[target]) {
            parents[target] = findParent(parents[target], parents);
        }
        return parents[target];
    }

    private static void unionParent(int a, int b, int[] parents) {
        int parentOfA = findParent(a, parents);
        int parentOfB = findParent(b, parents);

        if (parentOfA <= parentOfB) {
            parents[parentOfB] = parentOfA;
        } else {
            parents[parentOfA] = parentOfB;
        }
    }

    private static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
