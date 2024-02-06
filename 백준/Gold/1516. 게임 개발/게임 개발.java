import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static class Building {
        int index, time;
        public Building(int index, int time) {
            this.index = index;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // 건물 i후에 지어야 하는 건물들
        List<List<Integer>> children = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            // graph init
            children.add(new ArrayList<>());
        }

        int[] degree = new int[N]; // 차수
        int[] times = new int[N]; // 건설에 필요한 시간
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            times[i] = Integer.parseInt(st.nextToken());

            while (st.countTokens() > 1) {
                int parent = Integer.parseInt(st.nextToken()) - 1;
                children.get(parent).add(i);
                degree[i]++;
            }
        }

        PriorityQueue<Building> pq = new PriorityQueue<>(Comparator.comparingInt(b -> b.time));
        // 초기 차수가 0인 빌딩들 넣어주기
        for (int i = 0; i < N; i++) {
            if (degree[i] == 0) {
                pq.offer(new Building(i, times[i]));
            }
        }

        int[] answers = new int[N]; // 건물별 건설 시간 저장
        int globalTime = 0; // 전체 소요 시간 유지
        while (!pq.isEmpty()) {
            int currentTime = pq.peek().time;
            globalTime += currentTime;
            List<Building> current = new ArrayList<>();

            // 현재 빌딩과 동일한 잔여 시간을 가진 빌딩들 모두 뺴기
            while (!pq.isEmpty() && pq.peek().time == currentTime) {
                current.add(pq.poll());
            }

            // 나머지 빌딩에서도 시간 제거해주기
            for (Building building : pq) {
                building.time -= currentTime;
            }

            for (Building building : current) {
                answers[building.index] = globalTime;

                // 현재 빌딩의 자식들 처리
                for (int child : children.get(building.index)) {
                    degree[child]--;
                    if (degree[child] == 0) {
                        pq.offer(new Building(child, times[child]));
                    }
                }
            }
        }

        for (int answer : answers) {
            System.out.println(answer);
        }
    }

}