package start;

import javax.lang.model.element.AnnotationMirror;

public class AnimalDemo {
    public static void main(String[] args) {
        Animal cat = new Cat();
        cat.eat();
        cat.sleep();
        cat.sound();

        Animal monkey = new Monkey();
        monkey.eat();
        monkey.sleep();
        monkey.sound();

        Animal dog = new Dog();
        dog.eat();
        dog.sleep();
        dog.sound();
    }
}
