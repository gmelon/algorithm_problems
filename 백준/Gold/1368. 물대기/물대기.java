import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

        // 직접 우물을 파는 비용
        int[] digCosts = new int[N];
        for (int i = 0; i < digCosts.length; i++) {
            digCosts[i] = sc.nextInt();
        }

        // 물을 끌어오는 비용
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (i == j) {
                    sc.nextInt();
                    edges.add(new Edge(0, i, digCosts[i - 1]));
                    continue;
                }
                edges.add(new Edge(i, j, sc.nextInt()));
            }
        }
        sc.close();

        // 풀이 시작
        Collections.sort(edges);
        int[] parents = new int[N + 1];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
        List<Integer> currentWell = new ArrayList<>();

        int totalCost = 0; // 전제 설치 + 연결 비용
        for (Edge edge : edges) {
            int fromParent = findParent(parents, edge.from);
            int toParent = findParent(parents, edge.to);

            // 둘 모두 우물인 (+사이클 존재) 경우 패스
            if (fromParent == toParent) {
                continue;
            }
//            if (currentWell.contains(fromParent) && currentWell.contains(toParent)) {
//                continue;
//            }
            unionParent(parents, edge.from, edge.to);
            totalCost += edge.cost;

//            // 둘 중 우물이 하나도 없는 경우 먼저 비용이 작은 쪽으로 우물 생성
//            if (!currentWell.contains(fromParent) && !currentWell.contains(toParent)) {
//                if (digCosts[fromParent] > digCosts[toParent]) {
//                    currentWell.add(toParent);
//                    totalCost += digCosts[toParent];
//                } else {
//                    currentWell.add(fromParent);
//                    totalCost += digCosts[fromParent];
//                }
//            }
//
//            // 아래 부터는 둘 중 우물이 반드시 하나 이상은 존재
//            // 우물의 없는 쪽의 설치 비용 계산
//            int installCost = 0;
//            int installTarget = -1;
//            if (!currentWell.contains(fromParent)) {
//                installCost = digCosts[fromParent];
//                installTarget = fromParent;
//            } else {
//                installCost = digCosts[toParent];
//                installTarget = toParent;
//            }
//
//            if (edge.cost < installCost) {
//                // 둘 사이의 연결 비용이 더 적은 경우 -> 연결 진행
//                unionParent(parents, currentWell, edge.from, edge.to);
//                totalCost += edge.cost;
//            } else {
//                // 연결 비용이 설치 비용보다 더 비싼 경우 -> 설치를 진행
//                currentWell.add(installTarget);
//                totalCost += installCost;
//            }
        }
        System.out.println(totalCost);
    }

    public static int findParent(int[] parents, int targetIndex) {
        if (parents[targetIndex] != targetIndex) {
            parents[targetIndex] = findParent(parents, parents[targetIndex]);
        }
        return parents[targetIndex];
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
