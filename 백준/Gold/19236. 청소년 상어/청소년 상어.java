import java.util.Scanner;

public class Main {

    static int maxPoint = 0;

    // '상' 부터 반시계순
    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};

    static class Shark {

        Position position;
        int direction;
        int point;

        public Shark(Position position, int direction, int point) {
            this.position = position;
            this.direction = direction;
            this.point = point;
        }

        public Shark(Shark shark) {
            this.position = new Position(shark.position.x, shark.position.y);
            this.direction = shark.direction;
            this.point = shark.point;
        }
    }

    static class Position {

        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Fish {

        int number, direction;

        public Fish(int number) {
            this.number = number;
        }

        public Fish(Fish fish) {
            this.number = fish.number;
            this.direction = fish.direction;
        }
    }

    public static void main(String[] args) {
        Fish[][] fishes = new Fish[4][4];

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (j % 2 == 0) {
                    fishes[i][j / 2] = new Fish(sc.nextInt() - 1);
                } else {
                    fishes[i][j / 2].direction = sc.nextInt() - 1;
                }
            }
        }
        sc.close();

        // 상어가 최초에 (0, 0) 물고기를 먹음
        Shark shark = new Shark(new Position(0, 0), fishes[0][0].direction, (fishes[0][0].number + 1));
        fishes[0][0] = null;

        // 물고기 이동
        moveFishes(fishes, shark);

        // 상어 이동 -> 경우의 수 발생
        // 더 이상 상어가 이동할 수 없을 때 까지 진행 -> 최대 point 계산
        moveShark(fishes, shark);

        // 최종 point의 최대값 출력
        System.out.println(maxPoint);
    }

    static void moveFishes(Fish[][] fishes, Shark shark) {
        // 번호와 위치를 매핑하는 배열 생성
        // 상어와 빈 칸은 null
        Position[] positions = new Position[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (fishes[i][j] != null) {
                    positions[fishes[i][j].number] = new Position(i, j);
                }
            }
        }

        // 물고기 번호대로 이동을 시작
        move:
        for (int number = 0; number < 16; number++) {
            if (positions[number] == null) {
                continue;
            }

            for (int d = 0; d < 9; d++) {
                int curX = positions[number].x;
                int curY = positions[number].y;

                // 먼저 현재 방향으로 이동 가능한지 판단
                int nX = curX + dx[fishes[curX][curY].direction];
                int nY = curY + dy[fishes[curX][curY].direction];

                if (!isRangeValid(nX, nY) || (shark.position.x == nX && shark.position.y == nY)) {
                    // 이동 불가, 방향을 반시계로 돌린 후 다시 시도
                    fishes[curX][curY].direction = (fishes[curX][curY].direction + 1) % 8;
                    continue;
                }

                // 이동 가능
                Fish temp = fishes[curX][curY];
                if (fishes[nX][nY] != null) {
                    // 이동하려는 곳에 물고기가 있으면 현위치로 옮기기
                    positions[fishes[nX][nY].number].x = curX;
                    positions[fishes[nX][nY].number].y = curY;
                    fishes[curX][curY] = fishes[nX][nY];
                } else {
                    // 이동하려는 곳에 물고기가 없다면 현재 위치 비우기
                    fishes[curX][curY] = null;
                }
                // 현재 물고기 이동
                positions[temp.number].x = nX;
                positions[temp.number].y = nY;
                fishes[nX][nY] = temp;
                continue move;
            }
        }
    }

    // 경우의 수 발생하는 부분
    static void moveShark(Fish[][] fishes, Shark shark) {
        // 현재 상어의 방향에 위치한 물고기들을 먹고 이동하기를 반복한다
        int nX = shark.position.x;
        int nY = shark.position.y;

        while (isRangeValid(nX, nY)) {
            if (fishes[nX][nY] != null) {
                // 복제
                Shark copiedShark = new Shark(shark);
                Fish[][] copiedFishes = deepCopyFishes(fishes);

                // 잡아먹기 -> max point 기록
                copiedShark.position.x = nX;
                copiedShark.position.y = nY;
                copiedShark.direction = copiedFishes[nX][nY].direction;
                copiedShark.point += (copiedFishes[nX][nY].number + 1);

                maxPoint = Math.max(maxPoint, copiedShark.point);

                copiedFishes[nX][nY] = null;

                // 물고기 이동
                moveFishes(copiedFishes, copiedShark);
                // 상어 이동
                moveShark(copiedFishes, copiedShark);
            }

            nX += dx[shark.direction];
            nY += dy[shark.direction];
        }
    }

    static Fish[][] deepCopyFishes(Fish[][] fishes) {
        Fish[][] copiedFishes = new Fish[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (fishes[i][j] != null) {
                    copiedFishes[i][j] = new Fish(fishes[i][j]);
                }
            }
        }
        return copiedFishes;
    }

    static boolean isRangeValid(int x, int y) {
        return x >= 0 && x < 4 && y >= 0 && y < 4;
    }

}
