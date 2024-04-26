package start;

public class Dog extends Animal{

    public static void main(String[] args) {
        Animal dog = new Dog();
        dog.sound();
    }

    @Override
    void sound(){
        System.out.println("개가 울어요");
    }
    @Override
    void eat(){
        System.out.println("개가 먹어요");
    }
    @Override
    void sleep(){
        System.out.println("개가 자요");
    }
}
