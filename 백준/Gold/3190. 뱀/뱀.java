import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static class Rotation {
        int time;
        char direction;

        public Rotation(int time, char direction) {
            this.time = time;
            this.direction = direction;
        }

        public int nextDirectionIndex(int currentDirectionIndex) {
            if (direction == 'L') {
                currentDirectionIndex--;
                if (currentDirectionIndex == -1) {
                    return 3;
                }
            } else {
                currentDirectionIndex++;
                if (currentDirectionIndex == 4) {
                    return 0;
                }
            }
            return currentDirectionIndex;
        }
    }

    static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position movedPosition(int dx, int dy) {
            return new Position(x + dx, y + dy);
        }

        public boolean isOutOfBound(int N) {
            return x < 1 || x > N || y < 1 || y > N;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Position)) {
                return false;
            }
            Position p = (Position) obj;
            return this.x == p.x && this.y == p.y;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 보드의 크기

        int K = sc.nextInt(); // 사과의 개수
        List<Position> apples = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            apples.add(new Position(sc.nextInt(), sc.nextInt()));
        }

        int L = sc.nextInt(); // 방향 변환 횟수
        Queue<Rotation> rotations = new LinkedList<>();
        for (int i = 0; i < L; i++) {
            rotations.add(new Rotation(sc.nextInt(), sc.nextLine().trim().charAt(0)));
        }

        // 방향 정보, 동쪽부터 시계방향 동/남/서/북
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        // 뱀 몸통
        Deque<Position> snake = new LinkedList<>();

        // 초기 위치, 방향 설정
        int currentDirectionIndex = 0; // 0(동), 1(남), 2(서), 3(북)
        snake.offer(new Position(1, 1));

        // 시뮬레이션 시작
        int time = 0;
        while (!snake.isEmpty()) { // 벽에 부딪히거나 몸통과 만나면 종료
            time++;
            Position nextHead = snake.peekFirst().movedPosition(dx[currentDirectionIndex], dy[currentDirectionIndex]);
            // 벽이나 몸통과 부딪히는 경우
            if (nextHead.isOutOfBound(N) || snake.contains(nextHead)) {
                break;
            } else {
                // 계속해서 진행
                snake.offerFirst(nextHead); // 머리 늘리기
                if (!apples.contains(nextHead)) {
                    // 새로운 머리에 사과가 없으면 꼬리 하나 제거
                    snake.pollLast(); 
                } else {
                    // 사과가 있으면 사과 제거
                    apples.remove(nextHead);
                }
            }

            // 이동 완료 후 방향 전환이 있으면 수행
            if (!rotations.isEmpty() && rotations.peek().time == time) {
                currentDirectionIndex = rotations.poll().nextDirectionIndex(currentDirectionIndex);
            }
        } // 시뮬레이션 종료

        System.out.println(time);
    }

}
