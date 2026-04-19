import sys

sys.setrecursionlimit(10 ** 6)

input = sys.stdin.readline

n, m = map(int, input().split())

parent = [i for i in range(n + 1)]


def find_parent(x):
    if x == parent[x]:
        return x

    parent[x] = find_parent(parent[x])
    return parent[x]


def union(x, y):
    p_x = find_parent(x)
    p_y = find_parent(y)

    if p_x == p_y:
        return True

    parent[p_x] = p_y
    return False


for count in range(m):
    fr, to = map(int, input().split())

    if union(fr, to):
        print(count + 1)
        sys.exit(0)

print(0)
