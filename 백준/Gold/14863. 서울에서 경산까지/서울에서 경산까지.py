import sys

input = sys.stdin.readline

N, K = map(int, input().split())

# weight (index) -> 남은 배낭의 무게
# dp[weight] = max(dp[weight], dp[weight - cur_weight] + cur_value)

dp = [-1] * (K + 1)
dp[0] = 0

for _ in range(N):
    # 각 구간(N) 별로 반복
    walk_time, walk_value, bike_time, bike_value = map(int, input().split())

    # 이전 최대치에 도보/자전거를 각각 붙임
    for cur_time in range(K, -1, -1):
        cur_max_value = -1

        # 도보 검사
        if cur_time >= walk_time and dp[cur_time - walk_time] != -1:
            cur_max_value = max(cur_max_value, dp[cur_time - walk_time] + walk_value)

        # 자전거 검사
        if cur_time >= bike_time and dp[cur_time - bike_time] != -1:
            cur_max_value = max(cur_max_value, dp[cur_time - bike_time] + bike_value)

        # 현재 시간에서의 최대값으로 dp 갱신
        dp[cur_time] = cur_max_value

print(max(dp))
