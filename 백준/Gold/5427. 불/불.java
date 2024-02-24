import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static int w, h, ans;
    static char[][] map;
    static Point start;
    static int[] di = {-1, +1, 0, 0};
    static int[] dj = {0, 0, +1, -1};
    //    static boolean[][] possible;
    static Deque<Point> fire;

    static class Point {

        int i, j;

        public Point(int i, int j) {
            super();
            this.i = i;
            this.j = j;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int TC = Integer.parseInt(st.nextToken());
        for (int test = 0; test < TC; test++) {
            st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
            map = new char[h][w];
            fire = new ArrayDeque<>();
            ans = 0;

            for (int i = 0; i < h; i++) {
                String str = br.readLine();
                for (int j = 0; j < w; j++) {
                    map[i][j] = str.charAt(j);
                    if (map[i][j] == '@') {
                        start = new Point(i, j);
                    }
                    if (map[i][j] == '*') {
                        fire.add(new Point(i, j)); //불 좌표 저장
                    }
                }
            }

            escapeBuilding(start);

            if (ans == 0) {
                System.out.println("IMPOSSIBLE");
            } else {
                System.out.println(ans);
            }
        }

    }

    static void escapeBuilding(Point s) {
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(s.i, s.j));
        int cnt = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            int fireSize = fire.size();
            for (int f = 0; f < fireSize; f++) {
                spreadFire(fire.pollFirst());
            }

            for (int si = 0; si < size; si++) {
                Point now = q.poll();

                for (int d = 0; d < 4; d++) {
                    int newi = now.i + di[d];
                    int newj = now.j + dj[d];

                    if (isValid(newi, newj)) {
                        ans = cnt + 1;
                        return;
                    }

                    if (newi >= 0 && newj >= 0 && newi < h && newj < w && map[newi][newj] == '.') {
                        map[newi][newj] = '@';
                        q.add(new Point(newi, newj));
                    }
                }
            }
            cnt++;
        }
    }

    static void spreadFire(Point f) {
        int i = f.i;
        int j = f.j;
        for (int d = 0; d < 4; d++) {
            int newi = i + di[d];
            int newj = j + dj[d];

            if (newi >= 0 && newj >= 0 && newi < h && newj < w && map[newi][newj] != '#' && map[newi][newj] != '*') {
                map[newi][newj] = '*';
                fire.offerLast(new Point(newi, newj));

            }
        }
    }

    static boolean isValid(int i, int j) {
        if (i < 0 || j < 0 || i >= h || j >= w) {
            return true;
        } else {
            return false;
        }
    }


    static void printMap() {
        for (int i = 0; i < h + 2; i++) {
            for (int j = 0; j < w + 2; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("==============================");
    }

}