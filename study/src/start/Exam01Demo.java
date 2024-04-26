package start;

public class Exam01Demo {

    public static void main(String[] args) {
        int a = 10;
        int b = 3;
        Exam01 exam = new Exam01();
        System.out.println(exam.plus(a, b));
        System.out.println(exam.minus(a, b));
        System.out.println(exam.divide(a, b));
    }
}
