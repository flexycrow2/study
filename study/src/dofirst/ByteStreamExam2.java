package dofirst;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStreamExam2 {

    public static void main(String[] args) {
        //파일로부터 읽어서 -- 입력 통로가 필요
        //파일에 쓴다. -- 출력 통로가 필요.

        try(FileInputStream in = new FileInputStream("src/dofirst/ReadFile.java"); //이게뭐야
            FileOutputStream out = new FileOutputStream("outputFile.txt");) {
            int c;
            while((c = in.read()) != -1){
                out.write(c);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
