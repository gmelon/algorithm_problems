import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static class ShortCut {

        int from;
        int to;

        public ShortCut(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ShortCut shortCut = (ShortCut) o;
            return from == shortCut.from && to == shortCut.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return from + " " + to;
        }
    }

    /**
     * N x N 방에 단테씨가 갇힘 (N > 1) 좌측 상단이 1번방, 벽이나 이미 넘버링된 방과 만나면 **시계 방향**으로 돌며 넘버링, 정중앙방이 마지막 번호 N은 홀수이고, 따라서 가운데 방은 무조건
     * N^2
     * <p>
     * 단테는 자신의 방보다 번호가 크면서 이웃한 방으로만 이동 가능 1 -> N^2 방으로 이동하기 위해 K( < N^2 - 1)번만 사용하고 싶음
     * <p>
     * 이동하려는 방이 현재방 + 1 이 아니면 **지름길**
     */

    static int currentRunCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int runCount = sc.nextInt();
        for (int i = 0; i < runCount; i++) {
            solution(sc.nextInt(), sc.nextInt());
        }

        sc.close();
    }

    public static void solution(final int N, final int K) {
        /**
         * 빙글빙글 돌아가는 맵 만들기
         */
        int[][] map = new int[N + 1][N + 1];
        // 패딩 좌표는 -1로 초기화
        for (int i = 0; i < map.length; i++) {
            map[0][i] = -1;
            map[i][0] = -1;
        }
        // 벽에 박거나 이미 채워진 칸일 때 이동할 우선순위
        int direction = 0;
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        // 현재 좌표
        int currentX = 0;
        int currentY = 0;
        // 인덱스
        int index = 1;
        while (index <= N * N) {
            int nextX;
            int nextY;
            if (currentX == 0 && currentY == 0) {
                // 최초 순회 예외 케이스
                nextX = 1;
                nextY = 1;
            } else {
                nextX = currentX + dx[direction];
                nextY = currentY + dy[direction];
            }
            // 맵을 벗어나거나 이미 채워진 값이면 다음 방향으로 전환
            if (nextX < 1 || nextX > N || nextY < 1 || nextY > N || map[nextX][nextY] != 0) {
                direction++;
                if (direction == 4) {
                    direction = 0; // 범위를 벗어나면 초기화
                }
                continue;
            }

            // 맵 채우기
            map[nextX][nextY] = index;
            currentX = nextX;
            currentY = nextY;
            index++;
        }

        /**
         * 시뮬레이션 시작
         */
        List<ShortCut> shortCuts = new ArrayList<>();
        boolean canReach = dfs(map, shortCuts, 1, 1, K);

        if (canReach) {
            // 도달 가능
            System.out.println("Case #" + ++currentRunCount + ": " + shortCuts.size());
            for (ShortCut shortCut : shortCuts) {
                System.out.println(shortCut);
            }
        } else {
            System.out.println("Case #" + ++currentRunCount + ": IMPOSSIBLE");
        }
    }

    // 우, 하, 좌, 상
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static boolean dfs(final int[][] map, List<ShortCut> shortCuts, int currentX, int currentY, int K) {
        // 도달 완료
        int centerValue = (map.length - 1) * (map.length - 1);
        if (map[currentX][currentY] == centerValue && K == 0) {
            return true;
        }

        // 도달하지 못했다면 계속해서 탐색
        for (int i = 0; i < dx.length; i++) {
            int nextX = currentX + dx[i];
            int nextY = currentY + dy[i];
            // 범위를 벗어나면 제외
            if (nextX < 1 || nextX > map.length - 1 || nextY < 1 || nextY > map.length - 1) {
                continue;
            }
            // 다음 이동 가능한 칸이 현재 값보다 작은 수여도 제외 (탐색 불가능)
            if (map[currentX][currentY] > map[nextX][nextY]) {
                continue;
            }

            // 이동 가능한 칸에 대해서 dfs 수행
            // 탐색이 완료되면 바로 반환, 아니면 계속해서 탐색
            if (map[nextX][nextY] != map[currentX][currentY] + 1) {
                // 지름길이면 추가
                shortCuts.add(new ShortCut(map[currentX][currentY], map[nextX][nextY]));
            }
            boolean canReach = dfs(map, shortCuts, nextX, nextY, K - 1);
            if (canReach) {
                return true;
            } else {
                // 탐색 실패한 경우 중 지름길이 있었던 경우 다시 원복
                if (map[nextX][nextY] != map[currentX][currentY] + 1) {
                    // 지름길이면 추가
                    shortCuts.remove(new ShortCut(map[currentX][currentY], map[nextX][nextY]));
                }
            }
        }

        // 여기에 도달하면 도달 불가능
        return false;
    }


}

