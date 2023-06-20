import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String N = sc.nextLine();
        sc.close();

        System.out.println(solution(N));
    }

    public static String solution(final String N) {
        int lastOfLeftIndex = N.length() / 2 - 1;
        int sum = 0;
        for (int i = 0; i < N.length(); i++) {
            if (i <= lastOfLeftIndex) {
                sum += (N.charAt(i) - '0');
            } else {
                sum -= (N.charAt(i) - '0');
            }
        }
        if (sum == 0) {
            return "LUCKY";
        }
        return "READY";
    }
}
