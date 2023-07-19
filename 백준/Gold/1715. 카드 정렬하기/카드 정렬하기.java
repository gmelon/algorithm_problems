import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            queue.offer(sc.nextInt());
        }
        sc.close();

        // 풀이 시작
        int answer = 0;
        while (queue.size() > 1) {
            int first = queue.poll();
            int second = queue.poll();
            answer += first + second;
            queue.offer(first + second);
        }
        System.out.println(answer);
    }

}
