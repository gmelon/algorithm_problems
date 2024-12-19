import java.util.*;

class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        int playTime = toSecond(play_time);
        int advTime = toSecond(adv_time);

        int[] times = new int[360_000];
        for (String log : logs) {
            String[] split = log.split("-");

            int start = toSecond(split[0]);
            int end = toSecond(split[1]);
            for (int i = start; i < end; i++) {
                times[i]++;
            }
        }

        int maxIndex = 0;
        long acc = 0;
        for (int i = 0; i < advTime; i++) {
            acc += times[i];
        }
        long maxValue = acc;
        for (int i = advTime; i < playTime; i++) {
            acc += times[i] - times[i - advTime];
            if (acc > maxValue) {
                maxValue = acc;
                maxIndex = i - advTime + 1;
            }
        }

        return toString(maxIndex);
    }

    public int toSecond(String raw) {
        int[] times = Arrays.stream(raw.split(":"))
            .mapToInt(Integer::parseInt)
            .toArray();
        return 3600 * times[0] + 60 * times[1] + times[2];
    }

    public String toString(int seconds) {
        int hour = seconds / 3600;
        int minute = (seconds - 3600 * hour) / 60;
        int second = seconds - 3600 * hour - 60 * minute;

        return (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second;
    }
}
