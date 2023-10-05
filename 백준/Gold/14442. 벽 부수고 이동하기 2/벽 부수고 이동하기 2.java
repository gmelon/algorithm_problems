import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static class Node {
        int x, y, distance, wallCount;
    
        Node(int x, int y, int distance, int wallCount) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.wallCount = wallCount;
        }
    }   

    static final int[] dx = {0, 0, 1, -1};
    static final int[] dy = {1, -1, 0, 0};
    static int[][] map;
    static boolean[][][] visited;
    static int n, m, k, answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n][m];
        visited = new boolean[n][m][k + 1];

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j) - '0';
            }
        }

        answer = -1;

        bfs();

        System.out.println(answer);
    }

    public static void bfs() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(0, 0, 1, 0));
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentX = current.x;
            int currentY = current.y;

            if (currentX == n - 1 && currentY == m - 1) {
                answer = current.distance;
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = currentX + dx[i];
                int nextY = currentY + dy[i];

                if (0 > nextX || nextX >= n || 0 > nextY || nextY >= m) {
                    continue;
                }

                if (map[nextX][nextY] == 0 && !visited[nextX][nextY][current.wallCount]) {
                    visited[nextX][nextY][current.wallCount] = true;
                    queue.add(new Node(nextX, nextY, current.distance + 1, current.wallCount));
                } else {
                    if (current.wallCount < k && !visited[nextX][nextY][current.wallCount + 1]) {
                        visited[nextX][nextY][current.wallCount + 1] = true;
                        queue.add(new Node(nextX, nextY, current.distance + 1, current.wallCount + 1));
                    }
                }
            }
        }
    }

}
