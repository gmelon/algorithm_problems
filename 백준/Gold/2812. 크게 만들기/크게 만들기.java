import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();

        String number = sc.next();

        System.out.println(solution(N, K, number));
    }

    public static String solution(int N, int K, String number) {

        /**
         * number -> N 자리 숫자
         * K 개만큼 숫자를 지워야함
         */

        Stack<Integer> stack = new Stack<>();
        // 초기 값 설정
        stack.push(number.charAt(0) - '0');

        int index = 1;
        for (; index < number.length(); index++) {

            Integer current = number.charAt(index) - '0';
            // K > 0 일 동안 이전 스택에서 자신보다 작은 값들을 모두 제거
            while (K > 0 && !stack.isEmpty() && stack.peek() < current) {
                stack.pop();
                K--;
            }

            if (K == 0) {
                break;
            }

            // push 하는건 이전 값을 뺴는지 여부와 관계 없음
            stack.push(current);
        }

        // 계속해서 숫자가 작아지면 K가 소진되지 않을 수도 있음
        while (K > 0) {
            // K가 소진될 때 까지 뒤에서부터 숫자 제거
            // 이때는 index == number.length() 이므로 index는 고려하지 않아도 됨
            stack.pop();
            K--;
        }

        // K == 0 이 되면 현재까지의 stack 값들과 index이후의 number 값을 더해서 반환
        StringBuilder sb = new StringBuilder();
        for (Integer integer : stack) {
            sb.append(integer);
        }
        if (index < number.length()) {
            sb.append(number.substring(index));
        }

        return sb.toString();
    }
}
