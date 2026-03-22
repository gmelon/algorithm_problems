import sys

input = sys.stdin.readline

given = input().strip()

# 순열이니깐,,
# 1. 이후에 중복이 없도록 잘라야 함
# 2. 잘라진 숫자의 개수는 1~50개
# 3. 각 숫자의 크기는 제약 없음
# 4. 09 -> 이런건 안 될듯 / 무조건 0 은 이전 숫자 맨 뒤에 붙어야 함

# N 은 1~50
# 어차피 순서는 정해져있는 문자열이니깐
# 현재 위치에서 한자리를 쓸거냐? 아니면 두자리를 쓸거냐? 를 결정하면 됨

visited = [False for _ in range(51)]
answers = []

def dfs(cur_index):
    if cur_index >= len(given):
        # 1부터 빠짐없이 채워졌는지 추가 확인이 필요함
        max_value = max(answers)
        for i in range(1, max_value + 1):
            if not visited[i]:
                return False
        return True

    # 하나만 사용 시도
    value = int(given[cur_index:cur_index + 1])
    if value <= 50 and not visited[value] and value != 0:
        answers.append(value)
        visited[value] = True
        if dfs(cur_index + 1):
            return True
        else:
            answers.pop()  # 한 개로는 실패
            visited[value] = False

    # 두개 사용 시도
    value = int(given[cur_index:cur_index + 2])
    if cur_index + 2 <= len(given) and value <= 50 and not visited[value] and value != 0:
        answers.append(value)
        visited[value] = True
        if dfs(cur_index + 2):
            return True
        else:
            answers.pop()
            visited[value] = False
            return False

    # 여기 도달하면 안 된거
    return False


cur_index = 0
dfs(cur_index)

for answer in answers:
    print(answer, end=' ')
