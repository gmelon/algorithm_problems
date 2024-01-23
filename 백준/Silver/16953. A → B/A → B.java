import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static int count = 1_000_000_000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        sc.close();

        bfs(A, B);

        System.out.println(count == 1_000_000_000 ? -1 : count);
    }

    static class Node {
        int depth;
        long value;

        public Node(int depth, long value) {
            this.depth = depth;
            this.value = value;
        }
    }

    static void bfs(int init, int target) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(1, init));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.value == target) {
                count = Math.min(count, current.depth);
                continue;
            }

            if (current.value > target) {
                continue;
            }

            queue.offer(new Node(current.depth + 1, current.value * 2));
            queue.offer(new Node(current.depth + 1, current.value * 10 + 1));
        }

    }

}
