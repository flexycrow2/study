package chat_assign;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {
    public static void main(String[] args) {
        // 서버 소켓 생성
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("서버가 준비되었습니다.");
            // 여러명의 클라이언트의 정보를 기억할 공간
            Map<String, PrintWriter> chatClients = new HashMap<>();
            Map<Integer, List<PrintWriter>> chattingRooms = new HashMap<>(); // 방 번호, 인원 수

            while (true) {
                Socket socket = serverSocket.accept();

                new chat_assign.ChatThread(socket, chatClients, chattingRooms).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}