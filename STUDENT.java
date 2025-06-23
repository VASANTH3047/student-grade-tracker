import java.util.*;

public class StudentGradeTracker {
    static class Student {
        String name;
        ArrayList<Integer> grades = new ArrayList<>();

        Student(String name) {
            this.name = name;
        }

        void addGrade(int grade) {
            grades.add(grade);
        }

        double getAverage() {
            if (grades.isEmpty()) return 0;
            int sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            return (double) sum / grades.size();
        }

        int getHighest() {
            return grades.stream().max(Integer::compare).orElse(0);
        }

        int getLowest() {
            return grades.stream().min(Integer::compare).orElse(0);
        }

        @Override
        public String toString() {
            return name + " - Avg: " + String.format("%.2f", getAverage()) +
                   ", High: " + getHighest() + ", Low: " + getLowest();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int numStudents = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            Student student = new Student(name);

            System.out.print("Enter number of grades for " + name + ": ");
            int numGrades = scanner.nextInt();

            for (int j = 0; j < numGrades; j++) {
                System.out.print("Enter grade " + (j + 1) + ": ");
                int grade = scanner.nextInt();
                student.addGrade(grade);
            }
            scanner.nextLine(); // consume newline
            students.add(student);
        }

        System.out.println("\n--- Student Summary Report ---");
        for (Student s : students) {
            System.out.println(s);
        }

        scanner.close();
    }
}