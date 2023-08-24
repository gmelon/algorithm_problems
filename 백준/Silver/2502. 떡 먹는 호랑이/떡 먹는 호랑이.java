import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Day {

        int coefficientOfX;
        int coefficientOfY;

        public Day(int coefficientOfX, int coefficientOfY) {
            this.coefficientOfX = coefficientOfX;
            this.coefficientOfY = coefficientOfY;
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int D = sc.nextInt(); // 할머니가 넘어온 날
        int K = sc.nextInt(); // 호랑이에게 준 떡의 개수
        sc.close();

        // 풀이 시작
        List<Day> days = new ArrayList<>();
        days.add(new Day(1, 0)); // 1일차
        days.add(new Day(0, 1)); // 2일차
        // X와 Y의 계수 구하기
        for (int i = 2; i < D; i++) {
            Day minus2Day = days.get(i - 2);
            Day minus1Day = days.get(i - 1);
            days.add(new Day(minus2Day.coefficientOfX + minus1Day.coefficientOfX,
                minus2Day.coefficientOfY + minus1Day.coefficientOfY));
        }

        Day targetDay = days.get(D - 1);
        // 가능한 X와 Y찾기
        int answerX = 1;
        int answerY = 1;
        boolean found = false;

        for (answerX = 1; answerX < 100_000; answerX++) {
            for (answerY = answerX; answerY < 100_000; answerY++) {
                if (targetDay.coefficientOfX * answerX + targetDay.coefficientOfY * answerY == K) {
                    found = true;
                    break;
                }
                if (targetDay.coefficientOfX * answerX + targetDay.coefficientOfY * answerY > K) {
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        System.out.println(answerX);
        System.out.println(answerY);
    }

}
