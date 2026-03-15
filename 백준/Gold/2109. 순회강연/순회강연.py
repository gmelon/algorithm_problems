import heapq
import sys

input = sys.stdin.readline

n = int(input().strip())
inputs = [tuple(map(int, input().split())) for _ in range(n)]

# 무조건 현재 날짜에서 가장 비싼걸 찾는건 아님
    # 1일차인데 1일 남은걸 안 하고 2일 중에 고르는게 최선일 수도 있기 때문
    # 그럼 어케하지
# 반대로?
    # 예를 들어 3일차까지면 5일차,,부터 1일차까지 고른다고?
    # A 3 100 / B 3 200 / C 2 30 / D 2 20 / E 1 0 이라고 하면
    # B -> B -> C 순으로

if len(inputs) == 0:
    print(0)
    sys.exit()

# inputs 를 날짜순으로 정렬
inputs.sort(key=lambda x: -x[1])

max_d = max(d for p, d in inputs)
heap = []
next_value_index = 0
answer = 0

# 마지막 날짜부터
for today in range(max_d, 0, -1):
    # 마감일이 오늘인 애들을 heap 에 넣기
    while next_value_index < n and inputs[next_value_index][1] >= today:
        heapq.heappush(heap, -inputs[next_value_index][0])  # 가격을 내림차순으로 넣음
        next_value_index += 1

    # 현재 가능한 강연 중 가장 비싼거 선택
    if len(heap) > 0:
        answer += heapq.heappop(heap)

print(-answer)