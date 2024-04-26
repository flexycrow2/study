package start;

public class CupDemo {

    public static void main(String[] args) {
        Cup cup = new Cup();
        cup.get();
        cup.pour();
        System.out.println(cup.extendsSize());

        //extends size 메소드 구현해서 size 크기 2배 증가 시켜준 후 반환받기
    }
}
