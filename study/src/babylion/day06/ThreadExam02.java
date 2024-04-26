package babylion.day06;

public class ThreadExam02 {

    public static void main(String[] args) {
        System.out.println("main 시작");

        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();

        System.out.println("main 끝");
    }
}

class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("스레드에서 할 일 구현");
    }
}
