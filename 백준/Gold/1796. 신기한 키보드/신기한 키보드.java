import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.nextLine();
        sc.close();

        // 풀이 시작
        Set<Character> set = new HashSet<>();
        char[] alphabets = S.toCharArray();
        for (char alphabet : alphabets) {
            set.add(alphabet);
        }
        int alphabetCount = set.size();

        List<Character> sortedDistinctAlphabets = set.stream()
            .sorted()
            .collect(Collectors.toList());

        int[][] dp = new int[alphabetCount][2]; // dp[알파벳순서][왼쪽부터탐색-0, 오른쪽부터탐색-1] = 이동거리의 최소값

        // 초기 값 설정
        int left = findLeftEndIndex(alphabets, sortedDistinctAlphabets.get(0));
        int right = findRightEndIndex(alphabets, sortedDistinctAlphabets.get(0));
        int distance = right - left;
        dp[0][0] = left + distance; // 왼쪽에서 시작해, 오른쪽으로 탐색
        dp[0][1] = right + distance; // 오른쪽에서 시작해, 왼쪽으로 탐색

        for (int i = 1; i < alphabetCount; i++) {
            int prevLeft = left;
            int prevRight = right;

            left = findLeftEndIndex(alphabets, sortedDistinctAlphabets.get(i));
            right = findRightEndIndex(alphabets, sortedDistinctAlphabets.get(i));
            distance = right - left;

            // 이전 알파벳이 왼쪽에서 탐색을 시작했는지, 오른쪽에서 시작했는지에 따라
            // 현재 알파벳이 탐색해야 할 거리가 달라진다
            dp[i][0] = Math.min(dp[i - 1][0] + Math.abs(prevRight - left), dp[i - 1][1] + Math.abs(prevLeft - left)) + distance;
            dp[i][1] = Math.min(dp[i - 1][0] + Math.abs(prevRight - right), dp[i - 1][1] + Math.abs(prevLeft - right)) + distance;
        }

        System.out.println(Math.min(dp[alphabetCount - 1][0], dp[alphabetCount - 1][1]) + S.length());
    }

    static int findLeftEndIndex(char[] alphabets, char target) {
        for (int i = 0; i < alphabets.length; i++) {
            if (alphabets[i] == target) {
                return i;
            }
        }
        return -1; // 도달 불가
    }

    static int findRightEndIndex(char[] alphabets, char target) {
        for (int i = alphabets.length - 1; i >= 0; i--) {
            if (alphabets[i] == target) {
                return i;
            }
        }
        return -1; // 도달 불가
    }
}
