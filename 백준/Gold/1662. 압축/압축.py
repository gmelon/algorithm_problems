def str_len(given):
    if len(given) == 0 or given.isdigit():
        return len(given)

    cur_count = 0

    last_start_index = 0
    start_index = -1
    stack = []
    pre_fix = ""
    for i, g in enumerate(given):
        if g == '(':
            stack.append(g)
            if start_index == -1:
                start_index = i + 1
                pre_fix = given[last_start_index:i]
        if g == ')':
            stack.pop()
            if not stack:
                multiplier = int(pre_fix[-1])
                cur_count += (multiplier * str_len(given[start_index:i]) + len(pre_fix) - 1)
                last_start_index = i + 1
                start_index = -1

    if last_start_index < len(given):
        return cur_count + len(given[last_start_index:])
    return cur_count


S = input().strip()
print(str_len(S))
