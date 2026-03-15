import sys

input = sys.stdin.readline

n, m = map(int, input().split())
board = [list(map(int, input().strip())) for i in range(n)]
acc = [[0] * (m + 1) for i in range(n + 1)]


def sum(start, end):
    s_i, s_j = start
    e_i, e_j = end

    return acc[e_i + 1][e_j + 1] - acc[s_i][e_j + 1] - acc[e_i + 1][s_j] + acc[s_i][s_j]


# 가로로 3개
def case1():
    answer = 0

    for second_start in range(1, n - 1):
        for third_start in range(second_start + 1, n):
            answer = max(answer,
                         sum((0, 0), (second_start - 1, m - 1))
                         * sum((second_start, 0), (third_start - 1, m - 1))
                         * sum((third_start, 0), (n - 1, m - 1))
                         )

    return answer


# 세로로 3개
def case2():
    answer = 0

    for second_start in range(1, m - 1):
        for third_start in range(second_start + 1, m):
            answer = max(answer,
                         sum((0, 0), (n - 1, second_start - 1))
                         * sum((0, second_start), (n - 1, third_start - 1))
                         * sum((0, third_start), (n - 1, m - 1))
                         )

    return answer


# 좌측 상하 / 우측 전체
def case3():
    answer = 0

    for row in range(1, n):
        for col in range(1, m):
            answer = max(answer,
                         sum((0, 0), (row - 1, col - 1))
                         * sum((row, 0), (n - 1, col - 1))
                         * sum((0, col), (n - 1, m - 1))
                         )

    return answer


# 좌측 전체 / 우측 상하
def case4():
    answer = 0

    for col in range(1, m):
        for row in range(1, n):
            answer = max(answer,
                         sum((0, 0), (n - 1, col - 1))
                         * sum((0, col), (row - 1, m - 1))
                         * sum((row, col), (n - 1, m - 1))
                         )

    return answer


# 상단바 / 하단 좌우
def case5():
    answer = 0

    for row in range(1, n):
        for col in range(1, m):
            answer = max(answer,
                         sum((0, 0), (row - 1, m - 1))
                         * sum((row, 0), (n - 1, col - 1))
                         * sum((row, col), (n - 1, m - 1))
                         )

    return answer


# 하단바 / 상단 좌우
def case6():
    answer = 0

    for col in range(1, m):
        for row in range(1, n):
            answer = max(answer,
                         sum((0, 0), (row - 1, col - 1))
                         * sum((0, col), (row - 1, m - 1))
                         * sum((row, 0), (n - 1, m - 1))
                         )

    return answer


# 1. 전체 누적합 구하기
# (모든 숫자가 포함되어야 곱의 합의 max 가 최대일거라는 가정)
# acc[i + 1][j + 1] = 0.0 ~ i.j 까지의 합
for i in range(n):
    for j in range(m):
        acc[i + 1][j + 1] = board[i][j] + acc[i][j + 1] + acc[i + 1][j] - acc[i][j]

answer = 0
# 2. 직사각형을 구할 수 있는 각 케이스별로 전체 순회해서 최대값 갱신하기


answer = max(answer, case1())
answer = max(answer, case2())
answer = max(answer, case3())
answer = max(answer, case4())
answer = max(answer, case5())
answer = max(answer, case6())

print(answer)
