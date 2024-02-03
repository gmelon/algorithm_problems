import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Main {

    static int N;
    static int M;

    static Set<int[]> set = new TreeSet<>((int[] i1, int[] i2) -> {
        for (int i = 0; i < i1.length; i++) {
            if (i1[i] != i2[i]) {
                return i1[i] - i2[i];
            }
        }
        return 0; // 동일
    });
    static int[] numbers;
    static int[] arr;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        arr = new int[N];
        numbers = new int[M];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();

        Arrays.sort(arr);

        perm(0, 0);
        for (int[] i : set) {
            System.out.println(Arrays.stream(i).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
        }
    }

    static void perm(int index, int start) {
        if (index == M) {
            set.add(numbers.clone());
            return;
        }
        for (int i = start; i < N; i++) {
            numbers[index] = arr[i];
            perm(index + 1, i);
        }
    }
}