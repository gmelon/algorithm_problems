import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Node {
        int number, distance;

        public Node(int number, int distance) {
            this.number = number;
            this.distance = distance;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int root = sc.nextInt();

        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int distance = sc.nextInt();
            graph.get(from).add(new Node(to, distance));
            graph.get(to).add(new Node(from, distance));
        }
        sc.close();

        // 풀이 시작

        // 먼저 나무 기둥의 길이를 찾는다
        int body = 0; // 기둥 길이
        int currentHead = root;
        int gigaNumber = 0; // 기가 노드 번호

        boolean[] visited = new boolean[N + 1];
        visited[currentHead] = true;
        while (true) {
            // 자식이 두 개 이상인 노드를 만날때 까지 반복
            int neighborCount = 0;
            Node nextNode = null;
            for (Node neighbor : graph.get(currentHead)) {
                if (!visited[neighbor.number]) {
                    neighborCount++;
                    nextNode = neighbor;
                }
            }
            if (nextNode == null || neighborCount >= 2) {
                break;
            }
            body += nextNode.distance;
            currentHead = nextNode.number;
            visited[nextNode.number] = true;
        }

        gigaNumber = currentHead;

        // 이때부터는 dfs를 통해 가지 길이 중 최대를 구하기
        visited[gigaNumber] = true;
        int branch = dfs(gigaNumber, graph, visited, 0);

        System.out.println(body + " " + branch);
    }

    private static int dfs(int currentNumber, List<List<Node>> graph, boolean[] visited, int currentSum) {
        int max = 0;
        for (Node neighbor : graph.get(currentNumber)) {
            if (!visited[neighbor.number]) {
                visited[neighbor.number] = true;
                max = Math.max(max, dfs(neighbor.number, graph, visited, currentSum + neighbor.distance));
                visited[neighbor.number] = false;
            }
        }

        return Math.max(max, currentSum);
    }

}
