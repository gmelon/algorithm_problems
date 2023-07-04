import java.util.Scanner;

public class Main {

    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[] numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = sc.nextInt();
        }

        int plusCount = sc.nextInt();
        int minusCount = sc.nextInt();
        int multiplyCount = sc.nextInt();
        int divideCount = sc.nextInt();

        sc.close();

        // 풀이 시작
        search(1, numbers[0], numbers, plusCount, minusCount, multiplyCount, divideCount);

        System.out.println(max);
        System.out.println(min);
    }

    private static void search(int currentIndex, int currentResult,
        int[] numbers, int plusCount, int minusCount, int multiplyCount, int divideCount) {
        // 재귀 종료 케이스
        if (currentIndex == numbers.length) {
            max = Math.max(max, currentResult);
            min = Math.min(min, currentResult);
            return;
        }

        if (plusCount != 0) {
            search(currentIndex + 1, currentResult + numbers[currentIndex],
                numbers, plusCount - 1, minusCount, multiplyCount, divideCount);
        }
        if (minusCount != 0) {
            search(currentIndex + 1, currentResult - numbers[currentIndex],
                numbers, plusCount, minusCount - 1, multiplyCount, divideCount);
        }
        if (multiplyCount != 0) {
            search(currentIndex + 1, currentResult * numbers[currentIndex],
                numbers, plusCount, minusCount, multiplyCount - 1, divideCount);
        }
        if (divideCount != 0) {
            search(currentIndex + 1, currentResult / numbers[currentIndex],
                numbers, plusCount, minusCount, multiplyCount, divideCount - 1);
        }
    }

}
