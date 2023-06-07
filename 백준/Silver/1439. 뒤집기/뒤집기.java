import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        sc.close();

        System.out.println(solution(S));
    }

    public static int solution(final String S) {

        int groupZero = 0;
        int groupOne = 0;

        char prevValue = S.charAt(0);
        for (int i = 1; i < S.length(); i++) {
            if (prevValue != S.charAt(i)) {
                if (prevValue == '0') groupZero++;
                if (prevValue == '1') groupOne++;
            }
            prevValue = S.charAt(i);
        }
        if (prevValue == '0') groupZero++;
        if (prevValue == '1') groupOne++;

        return Math.min(groupZero, groupOne);
    }

}
