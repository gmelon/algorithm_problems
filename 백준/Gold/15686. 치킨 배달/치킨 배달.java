import java.util.ArrayList;
import java.util.List;
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
            if (!(o instanceof Position)) {
                return false;
            }
            Position p = (Position) o;
            return this.x == p.x && this.y == p.y;
        }

        public int calcDistance(Position other) {
            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt(); // 살릴 치킨집 개수

        List<Position> houses = new ArrayList<>();
        List<Position> chickens = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int value = sc.nextInt();
                if (value == 1) {
                    houses.add(new Position(i, j));
                } else if (value == 2) {
                    chickens.add(new Position(i, j));
                }
            }
        }

        // 재귀를 통해 치킨집들의 조합을 구현
        System.out.println(recursivelyFindAnswer(chickens, new ArrayList<>(), houses, M, 0));
    }

    public static int recursivelyFindAnswer(List<Position> chickens, List<Position> liveChickens, List<Position> houses, int targetDepth, int currentIndex) {
        // 각각의 집과 살아남은 치킨집간의 거리의 최소값들의 합을 구함
        if (liveChickens.size() == targetDepth) {
            int sumOfMin = 0; // 마을 전체의 치킨 거리

            for (Position house : houses) {
                int houseMin = Integer.MAX_VALUE; // 집 하나의 치킨 거리
                for (Position liveChicken : liveChickens) {
                    houseMin = Math.min(houseMin, house.calcDistance(liveChicken));
                }
                sumOfMin += houseMin;
            }

            return sumOfMin;
        }

        // 계속해서 재귀
        int min = Integer.MAX_VALUE;
        for (int i = currentIndex; i < chickens.size(); i++) {
            liveChickens.add(chickens.get(i));
            min = Math.min(min,
                recursivelyFindAnswer(chickens, liveChickens, houses, targetDepth, i + 1));
            liveChickens.remove(chickens.get(i));
        }

        return min;
    }

}
