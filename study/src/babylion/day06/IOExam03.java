package babylion.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class IOExam03 {

    public static void main(String[] args) {
        //파일에서부터 한줄씩 읽어서 콘솔에 출력하는 코드
        try(BufferedReader br = new BufferedReader(new FileReader("ioexam.txt"));
            PrintWriter pw = new PrintWriter("pweOutput.txt")
        ){
            String line;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
