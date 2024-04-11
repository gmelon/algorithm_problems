import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    static class Tree {

        int x, y;
        int age;

        public Tree(int x, int y, int age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }
    }

    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 땅 크기
        int M = Integer.parseInt(st.nextToken()); // 최초에 주어지는 나무 개수
        int K = Integer.parseInt(st.nextToken()); // K 년 후에 살아남은 나무의 수를 구하라

        // 겨울에 추가로 주어지는 양분의 양
        int[][] addedAmounts = new int[N][N];
        // 현재 땅의 양분의 양
        int[][] currentAmounts = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                addedAmounts[i][j] = Integer.parseInt(st.nextToken());
                currentAmounts[i][j] = 5; // 최초에 양분은 5
            }
        }

        // 현재 땅에 위치한 나무들을 저장하는 배열
        Deque<Tree>[][] treeMap = new ArrayDeque[N][N];
        // init
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                treeMap[i][j] = new ArrayDeque<>();
            }
        }

        // 초기 나무 입력
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int age = Integer.parseInt(st.nextToken());

            treeMap[x][y].add(new Tree(x, y, age));
        }

        // K년 동안 작업 진행
        for (int k = 0; k < K; k++) {

            // 봄
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    Deque<Tree> next = new ArrayDeque<>();
                    int addAmount = 0;

                    for (Tree current : treeMap[i][j]) {
                        if (currentAmounts[i][j] >= current.age) {
                            // 나무의 나이보다 양분이 많음
                            // 1. 양분 섭취
                            currentAmounts[i][j] -= current.age;
                            // 2. 나이 1 증가
                            current.age += 1;
                            next.offerLast(current);
                        } else {
                            // 양분이 부족해 (먹지 않고) 나무가 죽음
                            addAmount += current.age / 2;
                        }
                    }
                    treeMap[i][j] = next;

                    // 여름
                    currentAmounts[i][j] += addAmount;
                }
            }

            // 가을
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (Tree current : treeMap[i][j]) {
                        if (current.age % 5 != 0) {
                            // 번식 불가능
                            continue;
                        }

                        // 번식 가능
                        for (int d = 0; d < 8; d++) {
                            int nX = current.x + dx[d];
                            int nY = current.y + dy[d];

                            if (nX < 0 || nX >= N || nY < 0 || nY >= N) {
                                // 경계를 벗어나면 번식 X
                                continue;
                            }

                            // 번식
                            treeMap[nX][nY].offerFirst(new Tree(nX, nY, 1));
                        }
                    }
                }
            }

            // 겨울, 땅에 양분 추가
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    currentAmounts[i][j] += addedAmounts[i][j];
                }
            }
        }

        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                count += treeMap[i][j].size();
            }
        }
        System.out.println(count);
    }
}