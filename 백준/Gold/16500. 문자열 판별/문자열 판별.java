import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String S = sc.nextLine();

        int N = Integer.parseInt(sc.nextLine());
        String[] strings = new String[N];
        for (int i = 0; i < N; i++) {
            strings[i] = sc.nextLine();
        }

        sc.close();

        // 풀이 시작
        // i == 1 이면 0 ~ i 까지는 문자열을 구성할 수 있음을 의미
        int[] dp = new int[S.length() + 1];
        dp[0] = 1; // 문자열은 1부터 시작, 초기값 설정

        for (int i = 0; i < S.length(); i++) {
            if (dp[i] != 1) {
                // 0 ~ i 까지 끊기는 문자열 조합은 없음
                continue;
            }

            for (String string : strings) {
                // i + 1 부터의 문자열에 string이 포함된다면 마지막 위치를 1으로 변경
                if (i + string.length() <= S.length() && S.startsWith(string, i)) {
                    dp[i + string.length()] = 1;
                }
            }
        }

        // dp[S.length()] 가 1이면 가능
        System.out.println(dp[S.length()]);
    }

}
