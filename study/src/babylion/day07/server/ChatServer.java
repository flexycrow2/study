package babylion.day07.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    //객체가 1개만 생성되어야함. (나중엔 싱글턴 패턴으로 만들면 좋음)

//    1. ServerSocket 생성
//    2. ServerSocket은 accpet()로 대기한다. client가 접속하면
    public static void main(String[] args) {

        ClientManager clientManager = new ClientManager();
        ChatRoomManager chatRoomManager = new ChatRoomManager();

        try(ServerSocket serverSocket = new ServerSocket(12345)){ //1번구현
            while(true){
                //2번구현
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress() + "client가 접속했습니다.");

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //3. out 얻어옴
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //3. in 얻어옴

                //4. 처음 읽어들인 한 줄. 닉네임으로 받기
                String nickname = in.readLine();
                System.out.println(nickname + "닉네임의 사용자가 연결했습니다.");

                //5. Client 객체 생성. 5-2. clientManager 가 클라이언트를 알고 있어야한다. (클라이언트가 추가 되어야 함)
                Client client = new Client(nickname, in, out, socket);
                clientManager.addClient(client);

                //클라이언트가 접속하면 사용법 같은거 안내멘트를 어떤 객체가 갖는게 맞을까
                out.println("접속을 환영합니다. : " + nickname);
                out.println("사용방법");
                out.println("방 목록보기 : /list");
                out.println("방 생성 : /create");
                out.println("방 입장: /join 방번호");
                out.println("방 나가기 : /exit");
                out.println("접속 종료 : /bye");
                //고민해서 더 추가할 기능 있으면 추가해보고, 메소드로 만든다면 어떤 객체가 갖게할지 고민해서 빼보기.

                //6. ChatHandler (client)
                new ChatHandler(clientManager, chatRoomManager, client).start();

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
