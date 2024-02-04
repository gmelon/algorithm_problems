import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] arr = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < N + 1; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // 풀이 시작

        // 가장 긴 오름차순 부분 수열 찾기
        int[] ascDp = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            ascDp[i] = 1; // 자기 자신
            for (int j = i - 1; j > 0; j--) {
                if (arr[j] < arr[i] && ascDp[j] >= ascDp[i]) {
                    ascDp[i] = ascDp[j] + 1;
                }
            }
        }

        // 가장 긴 내림차순 부분 수열 찾기
        int[] descDp = new int[N + 1];
        for (int i = N; i > 0; i--) {
            descDp[i] = 1; // 자기 자신
            for (int j = i + 1; j < N + 1; j++) {
                if (arr[j] < arr[i] && descDp[j] >= descDp[i]) {
                    descDp[i] = descDp[j] + 1;
                }
            }
        }

        // 가장 긴 바이토닉 부분 수열 찾기
        int max = 0;
        for (int i = 1; i < N + 1; i++) {
            max = Math.max(max, ascDp[i] + descDp[i] - 1);
        }

        System.out.println(max);
    }

}