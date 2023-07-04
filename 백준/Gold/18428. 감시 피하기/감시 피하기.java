import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        sc.nextLine();

        char[][] arr = new char[N][N];
        List<Position> teachers = new ArrayList<>();
        List<Position> empties = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String[] split = sc.nextLine().split(" ");
            for (int j = 0; j < N; j++) {
                arr[i][j] = split[j].charAt(0);
                if (arr[i][j] == 'T') {
                    teachers.add(new Position(i, j));
                }
                if (arr[i][j] == 'X') {
                    empties.add(new Position(i, j));
                }
            }
        }

        sc.close();

        // 문제 풀이 시작

        // emties에서 3개를 택하는 모든 경우의 수를 구함
        boolean canAvoid = canAvoid(0, 0, new ArrayList<>(), empties, teachers, arr);
        if (canAvoid) {
            System.out.println("YES");
            return;
        }
        System.out.println("NO");
    }

    public static boolean canAvoid(int currentDepth, int startIndex, List<Position> selectedEmpties, List<Position> empties, List<Position> teachers, char[][] arr) {
        if (currentDepth == 3) {
            // 여기서 실제로 학생들이 감시를 피할 수 있는지 확인
            for (Position selectedEmpty : selectedEmpties) {
                arr[selectedEmpty.x][selectedEmpty.y] = 'O';
            }

            boolean reachable = checkReachable(teachers, arr);
            // 한번의 경우의 수라도 모든 학생에게 도달하지 못했다면 성공 케이스
            if (!reachable) {
                return true;
            }

            for (Position selectedEmpty : selectedEmpties) {
                arr[selectedEmpty.x][selectedEmpty.y] = 'X';
            }
            return false;
        }

        for (int i = startIndex; i < empties.size(); i++) {
            selectedEmpties.add(empties.get(i));
            if (canAvoid(currentDepth + 1, i + 1, selectedEmpties, empties, teachers, arr)) {
                return true;
            }
            selectedEmpties.remove(empties.get(i));
        }

        // 모든 경우의 수에서 학생에 도달함
        return false;
    }

    private static boolean checkReachable(List<Position> teachers, char[][] arr) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        for (Position teacher : teachers) {
            for (int i = 0; i < dx.length; i++) {
                int nextX = teacher.x;
                int nextY = teacher.y;

                while (true) {
                    nextX += dx[i];
                    nextY += dy[i];

                    if (nextX < 0 || nextX >= arr.length || nextY < 0 || nextY >= arr.length) {
                        break;
                    }
                    if (arr[nextX][nextY] == 'O') {
                        break;
                    }
                    if (arr[nextX][nextY] == 'S') {
                        // 한번이라도 학생에게 도달하면 도달 가능 (true)
                        return true;
                    }
                }
            }
        }

        // 한번도 학생에게 도달하지 못함
        return false;
    }

}
