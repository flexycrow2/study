package babylion.day06;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOExam02 {

    public static void main(String[] args) {
        //뮨저욜울 파일에 출력하는 코드 작성
        try (FileOutputStream fos = new FileOutputStream("output.txt")) {
            String output = "Hello, world!!";
            fos.write(output.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
