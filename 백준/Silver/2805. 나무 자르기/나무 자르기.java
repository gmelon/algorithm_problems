import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // 나무 개수
		int M = Integer.parseInt(st.nextToken()); // 가져가야 하는 나무의 길이

		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		long start = 0;
		long end = 1_000_000_000;
		long answer = 0;

		while (start <= end) {
			long mid = (start + end) / 2;

			long curM = 0;
			for (int i = 0; i < N; i++) {
				if (arr[i] > mid) {
					curM += arr[i] - mid;
				}
			}

			if (curM >= M) {
				start = mid + 1;
				answer = mid;
			} else {
				end = mid - 1;
			}
		}

		System.out.println(answer);

	}
}