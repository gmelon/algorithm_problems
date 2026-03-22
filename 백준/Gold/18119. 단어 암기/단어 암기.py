import sys

input = sys.stdin.readline

N, M = map(int, input().split())

words = [input().strip() for _ in range(N)]
queries = [input().split() for _ in range(M)]


# queries[0] == 1 -> 잊는다 # 바본가
# queries[0] == 2 -> 기억한다

def bit_masking(base_bit, alphabet, target_value):
    target_position = ord(alphabet) - ord('a')
    if target_value == 0:
        base_bit &= ~(1 << target_position)
        return base_bit
    if target_value == 1:
        base_bit |= (1 << target_position)
        return base_bit


def count(words_bit, current_brain):
    count = 0

    for word_bit in words_bit:
        if word_bit & current_brain == word_bit:
            count += 1

    return count


# words bit 만들기
words_bit = []
for word in words:
    current_word_bit = 0b0
    for a in word:
        current_word_bit = bit_masking(current_word_bit, a, 1)
    words_bit.append(current_word_bit)


# 처음엔 모든 알파벳을 알고 있는 상태
current_brain = (1 << 26) - 1

for query in queries:
    if query[0] == '1':
        current_brain = bit_masking(current_brain, query[1], 0)
    if query[0] == '2':
        current_brain = bit_masking(current_brain, query[1], 1)

    print(count(words_bit, current_brain))


