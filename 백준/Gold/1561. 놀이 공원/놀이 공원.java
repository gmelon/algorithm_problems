import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 아이들 명수
        int M = sc.nextInt(); // 놀이기구의 개수

        int[] playTimes = new int[M]; // 각 놀이기구의 운행 시간
        for (int i = 0; i < M; i++) {
            playTimes[i] = sc.nextInt();
        }
        sc.close();

        // 풀이 시작
        // 아이들 수가 전체 놀이기구 수 보다 작으면 바로 완료 가능
        if (playTimes.length >= N) {
            System.out.println(N);
            return;
        }

        // N명의 아이들이 놀이기구를 모두 탑승 완료하는데 걸리는 최소 시간 구하기
        long start = 0L;
        long end = 2_000_000_000 * 30L;
        long shortestTime = 0L;

        while(start <= end) {
            long mid = (start + end) / 2;

            long completedCount = playTimes.length;
            for (int playTime : playTimes) {
                completedCount += mid / playTime;
            }

            if (completedCount >= N) {
                shortestTime = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        // 최소 시간 - 1초 동안 몇 명의 아이들이 놀이기구를 탔는지 계산
        long lastBeforeShortestTime = shortestTime - 1;

        long lastBeforeCount = playTimes.length;
        for (int playTime : playTimes) {
            lastBeforeCount += lastBeforeShortestTime / playTime;
        }

        // 최소 시간 - 1초에 아직 놀이기구를 타지 못한 아이들 수
        int leftChildrenCount = (int) (N - lastBeforeCount);
        for (int i = 0; i < playTimes.length; i++) {
            if ((lastBeforeShortestTime + 1) % playTimes[i] == 0) {
                leftChildrenCount--;
                if (leftChildrenCount == 0) {
                    System.out.println(i + 1);
                    return;
                }
            }
        }

    }

}
