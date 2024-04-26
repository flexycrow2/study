package chat_assign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static chat_assign.CommandSet.COMMAND_SET;

public class ChatThread extends Thread {
    private Socket socket;
    private String nickName;
    private int roomNumber = 0; // 대기실 : 0, 나머지 방 : 1 이상
    private final Map<String, PrintWriter> chatClients;
    private final Map<Integer, List<PrintWriter>> chattingRooms;
    private BufferedReader in;
    private PrintWriter out;

    public ChatThread(Socket socket, Map<String, PrintWriter> chatClients, Map<Integer, List<PrintWriter>> chattingRooms) {
        this.socket = socket;
        this.chatClients = chatClients;
        this.chattingRooms = chattingRooms;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            nickName = in.readLine();

            System.out.println("'" + nickName + "'님이 접속했습니다.");
            System.out.println("'" + nickName + "'의 IP : " + socket.getLocalAddress().getHostAddress());
            System.out.println(in);
            // Map에 사용자 추가
            synchronized (chatClients) {
                chatClients.put(this.nickName, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendMsgToThisClient("\n반갑습니다. '" + nickName + "'님 채팅 서비스 접속을 환영합니다.");
        // 명령어 모음을 보내줘야 함
        sendMsgToThisClient(COMMAND_SET);

        // 명령어에 따라서 맞는 기능 수행
        String line;
        try {
            while ((line = in.readLine()) != null) {
                String[] msg = line.split(" "); // 0 : command, 1 : roomNum

                if (msg[0].equalsIgnoreCase("/bye")) {
                    bye();
                    break;
                } else if (msg[0].equalsIgnoreCase("/command")) {
                    sendMsgToThisClient(COMMAND_SET);
                } else if (msg[0].equalsIgnoreCase("/list")) {
                    showRoomList();
                } else if (msg[0].equalsIgnoreCase("/create")) {
                    createRoom();
                } else if (msg[0].equalsIgnoreCase("/join")) {
                    joinRoom(msg[1]);
                } else if (msg[0].equalsIgnoreCase("/exit")) {
                    getOutRoom();
                } else {
                    sendMsgToSameRoomClients(roomNumber, nickName + " : " + line);
                }

                /*
                접속종료 : /bye
                명령어 보기 : /command
                방 목록 보기 : /list
                방 생성 : /create
                방 입장 : /join [방번호]
                방 나가기 : /exit
                 */

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (out != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // 해당 클라이언트한테만 메시지를 보내는 메서드
    public void sendMsgToThisClient(String msg) {
        out.println(msg);
    }

    // 메시지를 특정 사용자한테만 보내는 메서드(귓속말)


    // 메시지를 같은 방(로비) 내 사용자들에게 보내는 메서드
    public void sendMsgToSameRoomClients(int roomNum, String msg) {
        synchronized (chatClients) {
            for (PrintWriter out : chattingRooms.get(roomNum)) {
                out.println(msg);
            }
        }
    }

    // 방 목록 보기
    public void showRoomList() {
        StringBuilder sb = new StringBuilder();

        sb.append("--------- 방 목록 --------\n");
        if (!chattingRooms.isEmpty()) {
            for (int roomNum : chattingRooms.keySet()) {
                sb.append("[채팅방 ").append(roomNum).append("] - [").append(chattingRooms.get(roomNum).size()).append("명 접속중]\n");
            }
            sb.append("--------------------------\n");
            sendMsgToThisClient(String.valueOf(sb));
        } else {
            sendMsgToThisClient("현재 생성된 채팅방이 없습니다.\n");
        }
    }

    // 방 생성 메서드
    public void createRoom() {
        // 방이 하나도 없으면 null -> 방 번호 1
        // 있으면 방 번호의 최댓값 + 1
        if (chattingRooms.isEmpty()) {
            this.roomNumber = 1;
        } else {
            int maxRoomNum = Integer.MIN_VALUE;
            for (int roomNum : chattingRooms.keySet()) {
                if (maxRoomNum < roomNum) {
                    maxRoomNum = roomNum;
                }
            }
            this.roomNumber = ++maxRoomNum;
        }

        // 방 생성시 인원수는 최초로, 1명
        synchronized (chattingRooms) {
            chattingRooms.put(roomNumber, new ArrayList<>(Arrays.asList(out)));
        }

        System.out.println("[" + roomNumber + "]번 방이 생성되었습니다.\n" +
                "'" + nickName + "'님이 [" + roomNumber + "]번 방에 입장했습니다.\n");
        sendMsgToThisClient("방 번호 [" + roomNumber + "]가 생성되었습니다.\n" +
                "[" + roomNumber + "]번 방에 입장했습니다.\n");
    }

    // 방 입장
    public void joinRoom(String num) {
        int roomNum;
        try {    // 사용자가 방 번호를 다른 문자로 입력했을 시 오류 처리
            roomNum = Integer.parseInt(num);
        } catch (Exception e) {
            System.out.println("방 번호를 정확히 입력해주세요.");
            return;
        }

        // 사용자가 이미 방에 들어와 있는 경우
        if (roomNumber != 0) {
            sendMsgToThisClient("현재 방에 들어와 있는 상태이므로 '/join' 명령어는 사용할 수 없습니다.\n");
        }
        // 입력한 방이 존재하지 않는 방일 경우 처리
        else if (!chattingRooms.containsKey(roomNum)) {
            sendMsgToThisClient("존재하지 않는 방 번호입니다. 다시 입력해주세요");
            showRoomList();
        }
        // 존재하는 방일 경우 join
        else {
            this.roomNumber = roomNum;
            synchronized (chattingRooms) {
                chattingRooms.get(roomNumber).add(out);
            }

            sendMsgToSameRoomClients(roomNum, "'" + nickName + "'님이 방에 입장했습니다.\n");
        }
    }

    // 방 나가기
    public void getOutRoom() {
        if (roomNumber == 0) {
            sendMsgToThisClient("현재는 방에 있는 상태가 아닙니다.\n");
            return;
        }
        synchronized (chattingRooms) {
            chattingRooms.get(roomNumber).removeIf(clientOut -> clientOut == out);

            // 방에 아무도 남지 않으면
            if (chattingRooms.get(roomNumber).isEmpty()) {
                chattingRooms.remove(roomNumber);
                System.out.println(nickName + "님이 [" + roomNumber + "]번 방을 나갑니다." +
                        "[" + roomNumber + "]번 방에 인원이 없어 삭제되었습니다.");
                sendMsgToThisClient("\n[" + roomNumber + "]번 방을 나갑니다.\n" +
                        "방에 아무도 없어 [" + roomNumber + "]번 방이 삭제되었습니다.\n");
            }
            // 방에 사람이 남아 있으면
            else {
                System.out.println(nickName + "님이 [" + roomNumber + "]번 방을 나갑니다.");
                sendMsgToSameRoomClients(roomNumber, nickName + "님이 방을 나갔습니다.");
                sendMsgToThisClient("[" + roomNumber + "]번 방을 나갑니다.\n");
            }
        }

        this.roomNumber = 0;
    }

    // bye
    public void bye() {
        synchronized (chatClients) {
            chatClients.remove(nickName);
        }
        System.out.println(nickName + "닉네임의 사용자가 연결을 끊었습니다.");
    }
}