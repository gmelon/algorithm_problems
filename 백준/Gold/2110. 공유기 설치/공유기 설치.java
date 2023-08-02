import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line);
        int N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        br.close();

        // 풀이 시작
        Arrays.sort(arr);

        int start = 1; // 공유기 사이 최소 거리
        int end = arr[arr.length - 1] - arr[0]; // 공유기 사이 최대 거리
        int answer = 1;
        while (start <= end) {
            // 가장 인접한 두 공유기 사이의 거리 (후보)
            int mid = (start + end) / 2;
            // 현재 mid로 설치 가능한 공유기의 개수
            int installCount = 1;
            int lastInstallIndex = 0;
            for (int i = 1; i < arr.length; i++) {
                // 설치 가능 조건
                if (arr[i] >= arr[lastInstallIndex] + mid) {
                    lastInstallIndex = i;
                    installCount++;
                }
            }
            // 문제에서 요구한 개수 이상 설치가 가능하면
            // 정답 갱신 후 값을 키워서 다시 탐색
            if (installCount >= C) {
                answer = mid;
                start = mid + 1;
            } else {
                // 조건이 만족되지 않으면 값을 줄여서 다시 탐색
                end = mid - 1;
            }
        }

        System.out.println(answer);
    }

}
