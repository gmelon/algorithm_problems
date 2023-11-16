import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<List<Integer>> graph;
    static List<Integer> fans;
    static boolean[] visited;
    static boolean alwaysFanMeeted = true;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 정점 개수
        int m = sc.nextInt(); // 간선 개수

        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            graph.get(sc.nextInt()).add(sc.nextInt());
        }
        int s = sc.nextInt(); // 팬클럽 위치한 정점 개수
        fans = new ArrayList<>();
        for (int i = 0; i < s; i++) {
            fans.add(sc.nextInt());
        }
        sc.close();

        // 풀이 시작
        visited = new boolean[n + 1];

        dfs(1);

        if (!alwaysFanMeeted) {
            System.out.println("yes");
            return;
        }
        System.out.println("Yes");
    }

    public static void dfs(int current) {
        if (fans.contains(current)) {
            return;
        }

        for (int neighbor : graph.get(current)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                dfs(neighbor);
                visited[neighbor] = false;
            }
        }

        if (graph.get(current).isEmpty()) {
            alwaysFanMeeted = false;
        }
    }

}
