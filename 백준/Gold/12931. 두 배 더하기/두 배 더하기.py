import sys

input = sys.stdin.readline

N = int(input().strip())
b = list(map(int, input().split()))

all_zero = False
count = 0
while not all_zero:
    all_zero = True
    divide_required = False
    holsu_count = 0

    for i in range(len(b)):
        if b[i] != 0:
            all_zero = False

        if b[i] % 2 != 0:
            holsu_count += 1
            b[i] -= 1

        if b[i] != 0:
            divide_required = True
            b[i] /= 2

    count += holsu_count
    if divide_required:
        count += 1

print(count)