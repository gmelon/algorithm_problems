import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int L = sc.nextInt();
        int R = sc.nextInt();

        int[][] arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        sc.close();

        // 문제 풀이 시작

        boolean isChanged = true;
        int changeDayCount = -1; // 종료되는 시점에 검사를 한 번 더 하므로 -1부터 시작
        List<Position> union = new ArrayList<>(); // 연합에 속한 나라들을 담을 리스트
        while (isChanged) {
            // 더 이상 변경되지 않으면 종료 한다
            isChanged = false;
            changeDayCount++;

            // 전체를 돌며 연합을 찾는다
            boolean[][] visited = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (visited[i][j]) {
                        continue;
                    }
                    int sum = bfs(arr, L, R, union, visited, new Position(i, j));

                    if (union.size() > 1) {
                        // bfs가 완료된 시점에 하나의 연합이 모두 계산됨
                        // 이때 해당 연합의 인구 이동을 진행
                        isChanged = true;

                        // 연합을 이루고 있는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루고 있는 칸의 개수)
                        int newSize = sum / union.size();
                        for (Position position : union) {
                            arr[position.x][position.y] = newSize;
                        }
                    }
                    // 현재 연합 초기화
                    union.clear();
                }
            }
        }

        System.out.println(changeDayCount);
    }

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    private static int bfs(int[][] arr, final int L, final int R,
        List<Position> union, boolean[][] visited, Position start) {
        Queue<Position> queue = new LinkedList<>();
        union.add(start);
        queue.offer(start);
        visited[start.x][start.y] = true;

        int sum = arr[start.x][start.y];
        while (!queue.isEmpty()) {
            Position current = queue.poll();

            // 이웃한 나라들 탐색
            for (int i = 0; i < 4; i++) {
                int nextX = current.x + dx[i];
                int nextY = current.y + dy[i];

                if (nextX < 0 || nextX >= arr.length || nextY < 0 || nextY >= arr.length
                || visited[nextX][nextY]) {
                    continue;
                }

                int difference = Math.abs(arr[current.x][current.y] - arr[nextX][nextY]);
                if (L <= difference && difference <= R) {
                    visited[nextX][nextY] = true;
                    sum += arr[nextX][nextY];
                    queue.offer(new Position(nextX, nextY));
                    union.add(new Position(nextX, nextY));
                }
            }
        }
        return sum;
    }

}
