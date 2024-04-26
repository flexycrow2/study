package start;

public class Monkey extends Animal{

    public static void main(String[] args) {
        Animal monkey = new Monkey();
        monkey.sleep();
    }

    @Override
    void sound(){
        System.out.println("원숭이가 울어요");
    }
    @Override
    void eat(){
        System.out.println("원숭이가 먹어요");
    }
    @Override
    void sleep(){
        System.out.println("원숭이가 자요");
    }
}
