package dofirst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class BufferedExam {

    public static void main(String[] args) {
        //1. 키보드로부터 5줄 입력 받아서 파일에 출력하는 프로그램을 작성해보세요.
        //2. 파일에 출력하는 프로그램을 작성해보세요.
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pw = new PrintWriter("test.txt");
        ) { //외워

            for(int i = 0; i < 5; i++){
                System.out.println("입력하세요.");
                String message = br.readLine();
                System.out.println(message);
                pw.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
