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
            // parents 배열 init
            parents[i] = i;
        }

        // 기존 친구 union
        for (int i = 0; i < M; i++) {
            unionParent(parents, sc.nextInt() - 1, sc.nextInt() - 1);
        }

        sc.close();

        Map<Integer, Group> groupMap = new HashMap<>(); // parents, count
        for (int i = 0; i < N; i++) {
            int parent = findParent(parents, i);
            
            groupMap.merge(parent, new Group(children[i], 1), (g1, g2) -> {
            	g1.candyCount += g2.candyCount;
            	g1.childrenCount += g2.childrenCount;
    			return g1;
            });
        }

        // Map을 list로 변환
        List<Group> groups = groupMap.values().stream()
            .collect(Collectors.toList());

        // 여기부터는 0-1 가방 문제
        int[][] table = new int[groups.size() + 1][K]; // K-1까지 가능
        for (int i = 1; i <= groups.size(); i++) {
            for (int k = 0; k <= K - 1; k++) {
                if (groups.get(i - 1).childrenCount > k) {
                    // 담을 수 없음
                    table[i][k] = table[i - 1][k];
                } else {
                    // 담을 수 있음
                    table[i][k] = Math.max(table[i - 1][k], table[i - 1][k - groups.get(i - 1).childrenCount] + groups.get(i - 1).candyCount);
                }
            }
        }

        System.out.println(table[groups.size()][K - 1]);
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
