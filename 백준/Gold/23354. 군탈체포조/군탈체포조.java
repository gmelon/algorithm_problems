import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static final int INF = 1_500_000_000;

    public static class Position {
        int x, y;
        int weight;

        public Position(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }
    }

    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        Position start = null; // 부대, 부대에서 출발하고 마지막에 복귀해야 함
        List<Position> deserters = new ArrayList<>(); // 탈영병들

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == -1) {
                    start = new Position(i, j, 0);
                }
                if (map[i][j] == 0) {
                    deserters.add(new Position(i, j, 0));
                }
            }
        }
        br.close();
        map[start.x][start.y] = 0;

        if (deserters.isEmpty()) {
            // 탈영병이 음슴
            System.out.println(0);
            return;
        }

        // 각 탈영병으로부터 모든 점까지의 최단거리를 구하기
        int[][][] minDists = new int[deserters.size() + 1][N][N]; // 0번은 부대
        findMinDist(start, minDists[0]); // 부대에서의 최단거리도 구하기
        for (int i = 0; i < deserters.size(); i++) {
            Position deserter = deserters.get(i);
            findMinDist(deserter, minDists[i + 1]);
        }

        // 어떤 탈영병부터 잡을지 순열 구하기
        // 마지막은 항상 부대로 복귀해야 함
        minCost = Integer.MAX_VALUE;
        selected = new int[deserters.size()];
        visited = new boolean[deserters.size()];

        permutation(deserters, start, minDists, 0);

        System.out.println(minCost);
    }

    static int minCost;
    static int[] selected;
    static boolean[] visited;

    static void permutation(List<Position> deserters, Position home, int[][][] minDists, int index) {
        if (index == deserters.size()) {
            // 비용 계산 & 갱신
            int cost = minDists[0][deserters.get(selected[0]).x][deserters.get(selected[0]).y];;
            for (int i = 0; i < index - 1; i++) {
                cost += minDists[selected[i] + 1][deserters.get(selected[i + 1]).x][deserters.get(selected[i + 1]).y];
            }
            cost += minDists[selected[index - 1] + 1][home.x][home.y];
            minCost = Math.min(minCost, cost);
            return;
        }
        for (int i = 0; i < deserters.size(); i++) {
            if (!visited[i]) {
                selected[index] = i;
                visited[i] = true;
                permutation(deserters, home, minDists, index + 1);
                visited[i] = false;
            }
        }
    }

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static void findMinDist(Position start, int[][] minDist) {
        PriorityQueue<Position> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.weight));
        // minDist init
        for (int[] ints : minDist) {
            Arrays.fill(ints, INF);
        }
        minDist[start.x][start.y] = 0;
        pq.offer(start);

        while (!pq.isEmpty()) {
            Position current = pq.poll();

            if (minDist[current.x][current.y] < current.weight) {
                continue;
            }

            // 인근 순회
            for (int d = 0; d < 4; d++) {
                int nX = current.x + dx[d];
                int nY = current.y + dy[d];

                if (nX < 0 || nX >= minDist.length || nY < 0 || nY >= minDist.length) {
                    continue;
                }

                int oldWeight = minDist[nX][nY];
                int newWeight = current.weight + map[nX][nY];
                if (oldWeight > newWeight) {
                    pq.offer(new Position(nX, nY, newWeight));
                    minDist[nX][nY] = newWeight;
                }
            }
        }
    }
}