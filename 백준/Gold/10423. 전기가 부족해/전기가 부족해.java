import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Cable implements Comparable<Cable> {
        int from;
        int to;
        int cost;

        public Cable(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Cable o) {
            return this.cost - o.cost;
        }
    }

    static class Node {

        int number;
        int cost;

        public Node(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 도시의 개수
        int M = sc.nextInt(); // 설치 가능한 케이블의 수
        int K = sc.nextInt(); // 발전소의 개수

        // 발전소 정보 입력
        List<Integer> powerStations = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            powerStations.add(sc.nextInt());
        }

        // 설치 가능한 케이블 정보 입력
        Cable[] cables = new Cable[M];
        for (int i = 0; i < M; i++) {
            cables[i] = new Cable(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }

        sc.close();

        // 풀이 시작
        int totalCost = 0;
        int[] parents = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            // 처음에는 모든 노드의 부모를 자기 자신으로 초기화
            parents[i] = i;
        }

        // 먼저 모든 노드에 대해 크루스칼 알고리즘을 수행
        Arrays.sort(cables);
        for (Cable cable : cables) {
            int parentOfFrom = findParent(parents, cable.from);
            int parentOfTo = findParent(parents, cable.to);
            // 두 노드 모두 발전소에 이미 연결되어 있으면 해당 간선은 제외
            if (powerStations.contains(parentOfFrom) && powerStations.contains(parentOfTo)) {
                continue;
            }

            // 발전소에 모두 연결되어 있지 않은 경우, 사이클이 발생하지 않을 때만 간선 포함
            if (findParent(parents, cable.from) != findParent(parents, cable.to)) {
                unionParent(parents, powerStations, cable.from, cable.to);
                totalCost += cable.cost;
            }
        }

        System.out.println(totalCost);

    }

    public static int findParent(int[] parents, int targetIndex) {
        if (parents[targetIndex] != targetIndex) {
            parents[targetIndex] = findParent(parents, parents[targetIndex]);
        }
        return parents[targetIndex];
    }

    public static void unionParent(int[] parents, List<Integer> powerStations, int a, int b) {
        int parentOfA = findParent(parents, a);
        int parentOfB = findParent(parents, b);

        // a나 b가 발전소와 연결되어 있다면 발전소를 부모로 갖도록 설정
        if (powerStations.contains(parentOfA)) {
            parents[parentOfB] = parentOfA;
        } else if (powerStations.contains(parentOfB)) {
            parents[parentOfA] = parentOfB;
        } else {
            if (parentOfA < parentOfB) {
                parents[parentOfB] = parentOfA;
            } else {
                parents[parentOfA] = parentOfB;
            }
        }
    }

}
