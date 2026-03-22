import sys

input = sys.stdin.readline

n, m = map(int, input().strip().split())
alpha_y = [[] for _ in range(26)]

for i in range(n):
    word = input().strip()
    alpha = [False] * 26 
    for c in word:
        alpha[ord(c) - ord("a")] = True

    for j in range(26):
        if alpha[j]:
            alpha_y[j].append(i)

result = [0 for i in range(n)]
forget = [False] * 26
ans = n

for _ in range(m):
    query, x = input().strip().split()

    if query == "1":
        if forget[ord(x) - ord("a")]:
            continue

        forget[ord(x) - ord("a")] = True

        for i in alpha_y[ord(x) - ord("a")]:
            if result[i] == 0:
                ans -= 1

            result[i] -= 1
    
    if query == "2":
        if not forget[ord(x) - ord("a")]:
            continue

        forget[ord(x) - ord("a")] = False

        for i in alpha_y[ord(x) - ord("a")]:
            result[i] += 1

            if result[i] == 0:
                ans += 1

    # print(result)
    print(ans)
    # print()

