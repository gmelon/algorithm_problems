import java.util.Scanner;

public class Main {

    static class Position {
        int x, y, direction;

        // 0 - 북, 1 - 동, 2 - 남, 3 - 서

        public Position(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public Position back() {
            switch (this.direction) {
                case 0:
                    return new Position(x + 1, y, direction);
                case 1:
                    return new Position(x, y - 1, direction);
                case 2:
                    return new Position(x - 1, y, direction);
                case 3:
                    return new Position(x, y + 1, direction);
                default:
                    throw new IllegalStateException();
            }
        }

        public Position forward() {
            switch (this.direction) {
                case 0:
                    return new Position(x - 1, y, direction);
                case 1:
                    return new Position(x, y + 1, direction);
                case 2:
                    return new Position(x + 1, y, direction);
                case 3:
                    return new Position(x, y - 1, direction);
                default:
                    throw new IllegalStateException();
            }
        }

        public void rotate() {
            direction--;
            if (direction == -1) {
                direction = 3;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        Position robotPosition = new Position(sc.nextInt(), sc.nextInt(), sc.nextInt());

        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        sc.close();

        // 풀이 시작
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        int answer = 0;
        while (true) {
            // 현재 칸이 아직 청소되지 않은 경우, 현재 칸을 청소
            if (map[robotPosition.x][robotPosition.y] == 0) {
                map[robotPosition.x][robotPosition.y] = -1;
                answer++; // 청소된 방 개수 증가
            }

            // 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 있는지 판단
            boolean hasUnCleanRoom = false;
            for (int i = 0; i < dx.length; i++) {
                int nextX = robotPosition.x + dx[i];
                int nextY = robotPosition.y + dy[i];

                if (map[nextX][nextY] == 0) {
                    // 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 있음
                    hasUnCleanRoom = true;
                }
            }

            if (!hasUnCleanRoom) {
                // 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 없는 경우
                Position backRobotPosition = robotPosition.back();
                if (map[backRobotPosition.x][backRobotPosition.y] != 1) {
                    // 바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 한 칸 후진하고
                    robotPosition = backRobotPosition;
                    // 1번으로 돌아간다.
                    continue;
                } else {
                    // 바라보는 방향의 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
                    break;
                }
            } else {
                // 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 있는 경우
                // 반시계방향 90도 회전
                robotPosition.rotate();

                // 바라보는 방향을 기준으로 앞쪽 칸이 청소되지 않은 빈 칸인 경우 한 칸 전진한다.
                Position forwardRobotPosition = robotPosition.forward();
                if (map[forwardRobotPosition.x][forwardRobotPosition.y] == 0) {
                    robotPosition = forwardRobotPosition;
                }

                // 1번으로 돌아간다.

            }
        }
        System.out.println(answer);
    }

}
