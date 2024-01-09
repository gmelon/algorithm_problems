import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static class Group {

        int candyCount = 0;
        int childrenCount = 0;

        public Group(int candyCount, int childrenCount) {
            this.candyCount = candyCount;
            this.childrenCount = childrenCount;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

        int[] children = new int[N];
        for (int i = 0; i < N; i++) {
            children[i] = sc.nextInt();
        }

        int[] parents = new int[N];
        for (int i = 0; i < parents.length; i++) {
            // init
            parents[i] = i;
        }

        // union
        for (int i = 0; i < M; i++) {
            unionParent(parents, sc.nextInt() - 1, sc.nextInt() - 1);
        }

        sc.close();

        Map<Integer, Group> count = new HashMap<>(); // parents, count
        for (int i = 0; i < N; i++) {
            int parent = findParent(parents, i);
            Group group = count.getOrDefault(parent, new Group(0, 0));

            count.put(parent, new Group(group.candyCount + children[i], group.childrenCount + 1));
        }

        // 한번에 먹어야 하는 사탕 개수, 오름차순
        List<Group> candies = count.values().stream()
            .sorted(Comparator.comparingInt(g -> g.childrenCount))
            .collect(Collectors.toList());

        // 여기부터는 0-1 가방 문제
        int[][] table = new int[candies.size() + 1][K]; // K-1까지 가능
        for (int i = 1; i <= candies.size(); i++) {
            for (int k = 0; k <= K - 1; k++) {
                if (candies.get(i - 1).childrenCount > k) {
                    // 담을 수 없음
                    table[i][k] = table[i - 1][k];
                } else {
                    // 담을 수 있음
                    table[i][k] = Math.max(table[i - 1][k], table[i - 1][k - candies.get(i - 1).childrenCount] + candies.get(i - 1).candyCount);
                }
            }
        }

        System.out.println(table[candies.size()][K - 1]);
    }

    static int findParent(int[] parents, int target) {
        if (parents[target] != target) {
            parents[target] = findParent(parents, parents[target]);
        }
        return parents[target];
    }

    static void unionParent(int[] parents, int a, int b) {
        int pA = findParent(parents, a);
        int pB = findParent(parents, b);

        if (pA < pB) {
            parents[pB] = pA;
        } else {
            parents[pA] = pB;
        }
    }

}
