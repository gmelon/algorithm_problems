import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static class Student implements Comparable<Student> {
        String name;
        int koreanScore;
        int englishScore;
        int mathScore;

        public Student(String name, String koreanScore, String englishScore, String mathScore) {
            this(name, Integer.parseInt(koreanScore), Integer.parseInt(englishScore), Integer.parseInt(mathScore));
        }

        public Student(String name, int koreanScore, int englishScore, int mathScore) {
            this.name = name;
            this.koreanScore = koreanScore;
            this.englishScore = englishScore;
            this.mathScore = mathScore;
        }

        @Override
        public int compareTo(Student o) {
            if (this.koreanScore != o.koreanScore) {
                return o.koreanScore - this.koreanScore;
            }
            if (this.englishScore != o.englishScore) {
                return this.englishScore - o.englishScore;
            }
            if (this.mathScore != o.mathScore) {
                return o.mathScore - this.mathScore;
            }
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.nextLine(); // 개행 제거

        Student[] students = new Student[N];
        for (int i = 0; i < N; i++) {
            String[] splitLine = sc.nextLine().split(" ");
            students[i] = new Student(splitLine[0], splitLine[1], splitLine[2], splitLine[3]);
        }
        sc.close();
        
        // 문제 풀이 시작
        Arrays.sort(students);

        for (Student student : students) {
            System.out.println(student.name);
        }
    }
}
