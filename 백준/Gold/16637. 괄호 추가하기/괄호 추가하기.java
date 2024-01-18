import java.util.Scanner;

public class Main {
    static char[] operators;
    static int[] operands;
    static int answer = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        operators = new char[N / 2];
        operands = new int[N / 2 + 1];

        String line = sc.next();
        for (int n = 0; n < N; n++) {
            if (Character.isDigit(line.charAt(n))) {
                operands[n / 2] = line.charAt(n) - '0';
            } else {
                operators[n / 2] = line.charAt(n);
            }
        }
        sc.close();

        // 풀이 시작
        test(operands[0], 0);

        System.out.println(answer);
    }

    static void test(int currentResult, int operatorIndex) {
        if (operatorIndex == operators.length) {
            // 모든 피연산자를 처리한 경우 최종 결과 계산
            answer = Math.max(answer, currentResult);
            return;
        }

        // 현재 피연산자에 괄호 적용 X
        test(calc(currentResult, operands[operatorIndex + 1], operators[operatorIndex]), operatorIndex + 1);

        // 현재 피연산자에 괄호 적용 O
        if (operatorIndex + 1 < operators.length) {
            int bracketResult = calc(operands[operatorIndex + 1], operands[operatorIndex + 2], operators[operatorIndex + 1]);
            test(calc(currentResult, bracketResult, operators[operatorIndex]), operatorIndex + 2);
        }
    }

    static int calc(int op1, int op2, char operator) {
        switch (operator) {
            case '+':
                return op1 + op2;
            case '-':
                return op1 - op2;
            default:
                return op1 * op2;
        }
    }
}
