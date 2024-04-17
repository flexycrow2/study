package dofirst;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ReadFile {

    public static void main(String[] args) throws Exception {
//        System.out.println(System.getProperty("user.dir"));
        FileInputStream fis = new FileInputStream("a.txt"); //파일을 a.txt에서 읽어와.
        FileOutputStream fos = new FileOutputStream("b.txt");
        int n;
        int cnt = 0;
        byte[] bytes = new byte[1024]; //시스템이 정한 크기?
//        while((n = fis.read(bytes)) != -1) { //bytes 배열을 읽어내서 n에 넣고 비교. 파일의 끝은 -1 약속. 이건 카운트가 1. 한번에 바이트 배열만큼 읽어. 효율적.
//            String str = new String(bytes);
//            System.out.println(str);
//            cnt++;
////            fos.write(bytes);
//        }

        while ((n =fis.read()) != -1) { //한 바이트씩 읽어와. 이건 카운트가 21
            System.out.println((char) n);
            cnt++;
            fos.write(n); //파일 쓰는건 운영체제.
        }

        System.out.println(cnt);
        fis.close();
        fos.close();
    }
}
