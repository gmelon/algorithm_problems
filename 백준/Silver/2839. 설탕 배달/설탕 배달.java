import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(solution(sc.nextInt()));

        sc.close();
    }

    public static int solution(int N) {
        // 설탕 봉지는 5키로, 3키로가 존재
        // 최대한 5키로 짜리를 많이 포함해 N을 구성하면 되는 문제

        // 5키로를 a개 썼을 때 나머지를 b개의 3키로로 구성할 수 있는지 확인하고,
        // 불가능 하면 a-- 하면서 최적의 a, b를 구하기
        // a == 0 인데도 b개의 3키로로 N 구성이 불가능하면 -1 반환

        for (int fiveKiloCount = N / 5; fiveKiloCount >= 0; fiveKiloCount--) {
            // fileKiloCount개의 5키로 봉지를 썼을 때 나머지를 모두 3키로로 채워서 N을 만들 수 있는가?
            if ((N - fiveKiloCount * 5) % 3 == 0) {
                return fiveKiloCount + (N - fiveKiloCount * 5) / 3;
            }
        }

        // 여기에 도달하면 모두 3키로로 구성해도 N을 만들 수 없음
        return -1;
    }

}
