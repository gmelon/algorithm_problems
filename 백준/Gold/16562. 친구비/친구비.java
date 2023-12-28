import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    static int[] parents;

    static class Fee implements Comparable<Fee> {
        int index, fee;

        public Fee(int index, int fee) {
            this.index = index;
            this.fee = fee;
        }

        @Override
        public int compareTo(Fee o) {
            return this.fee - o.fee;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 학생수
        int M = sc.nextInt(); // 관계수
        int K = sc.nextInt(); // 돈

        // 부모 배열 초기화
        parents = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            parents[i] = i;
        }

        // 친구 비용 입력받기
        PriorityQueue<Fee> fees = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            fees.offer(new Fee(i + 1, sc.nextInt()));
        }

        // 친구 관계 union 수행
        for (int i = 0; i < M; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();

            unionParent(from, to);
        }

        sc.close();

        // 풀이 시작
        int minCost = 0;
        while (!fees.isEmpty()) {
            Fee current = fees.poll();

            // 준석이가 0번 index
            if (findParent(current.index) != 0) {
                unionParent(current.index, 0);
                minCost += current.fee;
            }
        }

        // 비용이 비싸서 불가능
        if (minCost > K) {
            System.out.println("Oh no");
            return;
        }

        // 전체가 연결되지 않아서 불가능
        for (int i = 0; i < N + 1; i++) {
            if (findParent(i) != 0) {
                System.out.println("Oh no");
                return;
            }
        }

        System.out.println(minCost);
    }

    static int findParent(int a) {
        if (parents[a] != a) {
            parents[a] = findParent(parents[a]);
        }
        return parents[a];
    }

    static void unionParent(int a, int b) {
        int parentA = findParent(a);
        int parentB = findParent(b);

        if (parentA < parentB) {
            parents[parentB] = parentA;
        } else {
            parents[parentA] = parentB;
        }
    }

}
