package start;

public class Cup {

    int size = 700;
    String color;

    void pour() {
        System.out.println("물을 붓는다.");
    }

    void get() { //
        System.out.println("물을 뜬다.");
    }
    //extends size 메소드 구현해서 size 크기 2배 증가 시켜준 후 반환받기

    int extendsSize() {
//        int doubleSize = size * 2;
        return size *= 2;
    }
}

