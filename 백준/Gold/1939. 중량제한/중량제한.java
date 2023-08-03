import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static class Island {

        int name;
        int weight;

        public Island(int name, int weight) {
            this.name = name;
            this.weight = weight;
        }
    }

    static List<List<Island>> graph = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        // 그래프 초기화
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 한 번에 옮길수 있는 물품들의 중량의 최소/최대값
        int start = 0;
        int end = 0;

        for (int i = 0; i < M; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();
            int C = sc.nextInt();

            graph.get(A).add(new Island(B, C));
            graph.get(B).add(new Island(A, C));

            end = Math.max(end, C);
        }

        // 공장이 위치한 두 섬
        int A = sc.nextInt();
        int B = sc.nextInt();

        sc.close();

        // 풀이 시작
        int answer = 0;
        boolean[] visited;
        while (start <= end) {
            // 한 번에 옮길수 있는 물품들의 중량
            int mid = (start + end) / 2;
            visited = new boolean[N + 1];

            if (bfs(A, B, mid, visited)) {
                // 현재 중량으로 이동이 가능
                // mid를 조정해서 더 큰 값을 찾는다
                answer = mid;
                start = mid + 1;
            } else {
                // 현재 중량으로 이동이 불가
                end = mid - 1;
            }
        }

        System.out.println(answer);
    }

    // A -> B로 이동 시, mid 이하의 무게로 이동이 가능한지 판단하는 bfs 함수
    static boolean bfs(int A, int B, int mid, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(A);
        visited[A] = true;

        while (!queue.isEmpty()) {
            Integer current = queue.poll();

            if (current == B) {
                // 주어진 mid로 A->B에 도달 성공
                return true;
            }

            for (Island neighbor : graph.get(current)) {
                // current -> neighbor의 무게 제한이 mid 이하일 때만 넣기
                if (!visited[neighbor.name] && neighbor.weight >= mid) {
                    queue.offer(neighbor.name);
                    visited[neighbor.name] = true;
                }
            }
        }

        return false;
    }

}
