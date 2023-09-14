import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 행의 수
        int M = sc.nextInt(); // 열의 수
        sc.nextLine(); // 개행

        char[][] board = new char[N][M];
        for (int i = 0; i < N; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = line.charAt(j);
            }
        }
        sc.close();

        // 풀이 시작
        // 인덱스는 i * M + j 로 사용
        int[] parents = new int[N * M];
        // 최초엔 모든 노드를 자기 자신으로 초기화
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }

        // 모든 노드에 대해 한번씩 union 연산 수행
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int currentIndex = i * M + j;
                int targetIndex = 0;
                // union 타겟 인덱스 구하기
                if (board[i][j] == 'U') {
                    targetIndex = (i - 1) * M + j;
                } else if (board[i][j] == 'D') {
                    targetIndex = (i + 1) * M + j;
                } else if (board[i][j] == 'L') {
                    targetIndex = i * M + j - 1;
                } else if (board[i][j] == 'R') {
                    targetIndex = i * M + j + 1;
                }
                unionParent(parents, currentIndex, targetIndex);
            }
        }

        // 전체가 몇 개의 서로소 집합으로 구성되어있는지 확인
        Set<Integer> parentsSet = new HashSet<>();
        for (int i = 0; i < parents.length; i++) {
            parentsSet.add(findParent(parents, i));
        }

        System.out.println(parentsSet.size());
    }

    public static int findParent(int[] parents, int targetIndex) {
        if (parents[targetIndex] != targetIndex) {
            parents[targetIndex] = findParent(parents, parents[targetIndex]);
        }
        return parents[targetIndex];
    }

    public static void unionParent(int[] parents, int a, int b) {
        int parentOfA = findParent(parents, a);
        int parentOfB = findParent(parents, b);

        if (parentOfA < parentOfB) {
            parents[parentOfB] = parentOfA;
        } else {
            parents[parentOfA] = parentOfB;
        }
    }

}
