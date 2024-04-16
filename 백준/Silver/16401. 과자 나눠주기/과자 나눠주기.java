import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken()); // 조카 명수
        int N = Integer.parseInt(st.nextToken()); // 과제 개수

        int start = 1;
        int end = 0;

        int[] snacks = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            snacks[i] = Integer.parseInt(st.nextToken());
            end = Math.max(end, snacks[i]);
        }

        int answer = 0;
        while (start <= end) {
            int mid = (start + end) / 2; // 현재 과자 길이

            // 판단
            int count = 0; // 가능한 과자 개수
            for (int i = 0; i < N; i++) {
                count += snacks[i] / mid;
            }

            if (count >= M) {
                // 가능, 길이 늘리기
                answer = mid;
                start = mid + 1;
            } else {
                // 불가능, 길이 줄이기
                end = mid - 1;
            }
        }

        System.out.println(answer);
    }
}