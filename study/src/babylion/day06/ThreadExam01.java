//package babylion.day06;
//
//public class ThreadExam01 {
//
//    public static void main(String[] args) {
//        System.out.println("메인 시작");
//        MyThread myThread = new MyThread();
//        myThread.start();
//
//        try{
//            Thread.sleep(1000);
//        }catch (InterruptedException e){
//            throw new RuntimeException(e);
//        }
//        System.out.println("메인 끝");
//    }
//
//    class MyThread extends Thread{
//
//        @Override
//        public void run() {
//            try{
//                sleep(1000);
//            } catch (InterruptedException e){
//                throw new InterruptedException();
//            }
//            System.out.println("스레드에서 실제 하고자 하는 일 구현.");
//        }
//    }
//}
