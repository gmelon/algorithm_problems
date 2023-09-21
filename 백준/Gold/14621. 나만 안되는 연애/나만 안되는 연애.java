import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static class Edge implements Comparable<Edge> {

        int from;
        int to;
        int cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        sc.nextLine(); // 개행 제거

        String[] univercities = new String[N + 1];
        String[] temp = sc.nextLine().split(" ");
        for (int i = 1; i < N + 1; i++) {
            univercities[i] = temp[i - 1];
        }

        Edge[] edges = new Edge[M];
        for (int i = 0; i < M; i++) {
            edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }

        sc.close();

        // 풀이 시작
        Arrays.sort(edges);
        int[] parents = new int[N + 1];
        int totalCost = 0;
        for (int i = 1; i < N + 1; i++) {
            parents[i] = i;
        }

        for (Edge edge : edges) {
            if (findParent(parents, edge.from) == findParent(parents, edge.to)) {
                continue;
            }

            // 남자, 여자 대학교가 번갈아가며 나오도록
            if (univercities[edge.from].equals(univercities[edge.to])) {
                continue;
            }

            unionParent(parents, edge.from, edge.to);
            totalCost += edge.cost;
        }

        for (int i = 1; i < N + 1; i++) {
            parents[i] = findParent(parents, i);
            if (i != 1) {
                if (parents[i - 1] != parents[i]) {
                    System.out.println(-1);
                    return;
                }
            }
        }

        System.out.println(totalCost);
    }

    public static int findParent(int[] parents, int target) {
        if (parents[target] != target) {
            parents[target] = findParent(parents, parents[target]);
        }
        return parents[target];
    }

    public static void unionParent(int[] parents, int a, int b) {
        int parentA = findParent(parents, a);
        int parentB = findParent(parents, b);

        if (parentA < parentB) {
            parents[parentB] = parentA;
        } else {
            parents[parentA] = parentB;
        }
    }

}
