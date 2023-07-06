import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static boolean flag = true;

    static class Circle {

        int number;
        int color;

        List<Circle> neighbors = new ArrayList<>();

        public Circle(int number, int color) {
            this.number = number;
            this.color = color;
        }

        void addNeighbor(Circle circle) {
            neighbors.add(circle);
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        List<List<Circle>> testCases = new ArrayList<>();
        for (int i = 0; i < T; i++) {
            List<Circle> testCase = new ArrayList<>();

            int N = sc.nextInt();
            int M = sc.nextInt();

            // 그래프 초기화
            for (int j = 0; j <= N; j++) {
                testCase.add(new Circle(j, -1));
            }

            // 간선 양방향 구성
            for (int j = 0; j < M; j++) {
                int from = sc.nextInt();
                int to = sc.nextInt();

                testCase.get(from).addNeighbor(testCase.get(to));
                testCase.get(to).addNeighbor(testCase.get(from));
            }
            testCases.add(testCase);
        }

        sc.close();

        for (List<Circle> testCase : testCases) {
            // 전체 테스트 케이스 T 개에 대해 반복
            flag = true;

            boolean[] visited = new boolean[testCase.size()];

            // 전체에 대해 탐색하면서 몇 개의 그룹으로 이뤄져 있는지 확인
            for (int j = 1; j < testCase.size(); j++) {
                if (!visited[j]) {
                    visited[j] = true;
                    testCase.get(j).color = 0;
                    dfs(testCase, visited, j);
                }
            }

            if (flag) {
                System.out.println("possible");
            } else {
                System.out.println("impossible");
            }
        }
    }

    public static void dfs(List<Circle> graph, boolean[] visited, int current) {
        for (Circle neighbor : graph.get(current).neighbors) {
            if (!visited[neighbor.number]) {
                visited[neighbor.number] = true;
                if (graph.get(current).color == 0) {
                    neighbor.color = 1;
                } else {
                    neighbor.color = 0;
                }

                // 이웃이 2개 이상이면서 서로의 값이 다르면 X
                if (neighbor.neighbors.size() >= 2) {
                    if (neighbor.neighbors.stream()
                        .map(n -> n.color)
                        .filter(color -> color != -1) // 아직 안 칠해진 건 제외
                        .collect(Collectors.toSet()).size() >= 2) {
                        flag = false;
                        return;
                    }
                }
                dfs(graph, visited, neighbor.number);
            }
        }
    }

}
