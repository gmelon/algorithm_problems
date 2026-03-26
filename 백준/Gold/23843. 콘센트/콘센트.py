import sys

input = sys.stdin.readline

N, M = map(int, input().split())
req_times = list(map(int, input().split()))

req_times.sort(reverse=True)

# 각 충전기별 필요한 남은 시간
queue = [0 for i in range(M)]

next_device_index = 0
total_time = 0
finished = False

while not finished:
    finished = True

    # 남은 시간이 없는 queue 에 신규 할당
    min_queue_time = 1000000
    for i in range(len(queue)):
        if queue[i] == 0 and next_device_index < len(req_times):
            queue[i] = req_times[next_device_index]
            next_device_index += 1
        if queue[i] > 0:
            min_queue_time = min(min_queue_time, queue[i])

    # 가장 짧게 남은 시간만큼 차감
    total_time += min_queue_time
    for i in range(len(queue)):
        if queue[i] > 0:
            queue[i] -= min_queue_time
        if queue[i] != 0 or next_device_index < len(req_times):
            finished = False

print(total_time)
