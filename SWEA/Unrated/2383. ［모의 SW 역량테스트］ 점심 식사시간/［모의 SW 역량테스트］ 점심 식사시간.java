import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
 
public class Solution {
 
    static class Position {
        int x, y;
 
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
 
        Person toPerson(Stair stair) {
            return new Person(Math.abs(this.x - stair.x) + Math.abs(this.y - stair.y), stair.k);
        }
    }
 
    static class Person implements Comparable<Person> {
        int distance, k;
 
        public Person(int distance, int k) {
            this.distance = distance;
            this.k = k;
        }
 
        @Override
        public int compareTo(Person o) {
            if (this.k == o.k) {
                return this.distance - o.distance;
            }
            return this.k - o.k;
        }
    }
 
    static class Stair {
        int x, y, k;
 
        public Stair(int x, int y, int k) {
            this.x = x;
            this.y = y;
            this.k = k;
        }
    }
 
    static List<Position> person;
    static List<Stair> stairs;
    static boolean[] selected;
 
    static int minTime;
 
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
 
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
 
            person = new ArrayList<>();
            stairs = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    int value = Integer.parseInt(st.nextToken());
                    if (value == 1) {
                        person.add(new Position(i, j));
                    } else if (value > 1) {
                        stairs.add(new Stair(i, j, value));
                    }
                }
            }
 
            // 사람의 부분 집합 구하기
            selected = new boolean[person.size()];
            minTime = Integer.MAX_VALUE;
            powerSet(0);
 
            sb.append("#").append(tc).append(" ").append(minTime - 1).append("\n");
        }
        System.out.println(sb);
    }
 
    static void powerSet(int index) {
        if (index == selected.length) {
            // 부분 집합 선택 완료
            down(); // 내려가라
            return;
        }
 
        selected[index] = true;
        powerSet(index + 1);
        selected[index] = false;
        powerSet(index + 1);
    }
 
    static void down() {
        // 먼저 계단 별 큐 생성
        PriorityQueue<Person> trueStair = new PriorityQueue<>(); // 0번 계단
        PriorityQueue<Person> falseStair = new PriorityQueue<>(); // 1번 계단
 
        // 부분 집합 별 거리로 큐에 넣기
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                trueStair.offer(person.get(i).toPerson(stairs.get(0)));
            } else {
                falseStair.offer(person.get(i).toPerson(stairs.get(1)));
            }
        }
 
        // 각 계단 별 내려가는데 걸리는 시간 계산 후 최소 시간 갱신
        minTime = Math.min(Math.max(calcTime(trueStair), calcTime(falseStair)), minTime);
    }
 
    static int calcTime(PriorityQueue<Person> pq) {
        int time = 0;
        Queue<Person> temp = new ArrayDeque<>();
        while (!pq.isEmpty()) {
            for (int capacity = 0; capacity < 3 && !pq.isEmpty();) {
                if (time <= pq.peek().distance) {
                    // 아직 출발 불가능
                    break;
                }
 
                Person current = pq.poll();
                current.k -= 1;
                if (current.k >= 0) {
                    // 한 칸 내려감
                    temp.offer(current);
                    capacity++; // 동시에 내려가고 있는 사람 수
                }
                // 현재 감소로 계단을 전부 내려가면 용량 증가 X
            }
            while (!temp.isEmpty()) {
                pq.offer(temp.poll());
            }
            time++;
        }
        return time;
    }
}