package babylion.day06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IOExam01_1 {

    public static void main(String[] args) {
        //파일로부터 읽어와서 콘솔에 출력하는 코드를 작성하기
        try (FileInputStream fis = new FileInputStream("ioexam.txt");) {
            int i;
            int count = 0;
//            while((i = fis.read()) != -1){
//                count++;
//                System.out.print((char) i);
//            }
            byte[] b = new byte[512];
            while((i = fis.read(b)) != -1){
                count++;
                String s = new String(b,0,i);
                System.out.println(s);
            }

            System.out.println("총 " + count + " 회 읽어옴.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("파일이 존재하지 않아요.");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("파일로부터 입력 받는 중에 예외가 발생했어요.");
        }
    }
}
