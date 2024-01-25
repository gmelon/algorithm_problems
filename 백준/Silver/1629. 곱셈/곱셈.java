import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long a = sc.nextInt();
        long b = sc.nextInt();
        long c = sc.nextInt();

        sc.close();

        System.out.println(calc(a, b, c));
    }

    static long calc(long a, long b, long c) {
        if (b == 1) {
            return a % c;
        }

        long temp = calc(a, b / 2, c) % c;
        if (b % 2 == 0) {
            return (temp * temp) % c;
        }
        return ((temp * temp % c) * a) % c;
    }
}
