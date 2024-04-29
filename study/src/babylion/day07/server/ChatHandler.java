package babylion.day07.server;

import java.io.IOException;
import java.util.List;

public class ChatHandler extends Thread{
    private ClientManager clientManager;
    private ChatRoomManager chatRoomManager;
    private Client client;
    private ChatRoom chatRoom; // null 인 경우는 아직 대화방에 참여하지 않은 상태. Lobby 에 있는 경우.


    ChatHandler(ClientManager clientManager, ChatRoomManager chatRoomManager, Client client){
        this.chatRoomManager = chatRoomManager;
        this.client = client;
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        while (true){
            try{
                String message = client.getIn().readLine();
                if(message == null){
                    System.out.println(client.getNickname() + "닉네임 사용자가 연결을 끊었습니다.");
                    client.close();
                    return;
                }

                if(message.indexOf("/") == 0){ // 0번 인덱스의 문자열이 "/" 라면. /로 시작하면 특별한 명령이라고 약속.
                    if("/list".equals(message)){
                        //채팅룸 리스트를 보여주면.
                        List<ChatRoom> chatRooms = chatRoomManager.getChatRooms();
                        for(ChatRoom chatRoom : chatRooms){
                            client.getOut().println(chatRoom.getId());
                        }
                    } else if ("/create".equals(message)) {
                        System.out.println("create");
                        //방을 생성하고, 생성된 방에 client를 입장시킴
                        chatRoom = chatRoomManager.createChatRoom();
                        //채팅룸 클라이언트 리스트에 client 추가
                        chatRoom.addClient(client);
                        System.out.println("채팅방 생성 : " + chatRoom.getId());

                        client.getOut().println("채팅방이 생성되었습니다. 방번호 : " + chatRoom.getId());
                        chatRoom.broadcast(client.getNickname() + "님이 입장하셨습니다.");
                    } else if (message.indexOf("/join") == 0) { //     /join 방번호로 들어와야함
                        String[] tokens = message.split(" ");
                        int chatRoomId = -1;
                        try{
                            chatRoomId = Integer.parseInt(tokens[1]);  //      /join
                            chatRoom = chatRoomManager.getChatRoom(chatRoomId);
                            chatRoom.addClient(client);
                            chatRoom.broadcast(client.getNickname() + "님이 입장하셨습니다.");


                        }catch (Exception e){
                            //방 번호를 입력하지 않았을 때 예외처리
                            client.getOut().println("방번호가 정확하지 않아요. " + chatRoomId);
                        }
                    } else if ("/exit".equals(message)){
                        //채팅방에서만 빠져나감.
                        if(chatRoom != null){
                            chatRoom.broadcast(client.getNickname() + "님이 방을 나갔습니다.");
                            chatRoom.removeClient(client);
                            chatRoom = null;
                        }else {
                            client.getOut().println("현재 채팅방이 아닙니다.");
                        }

                    } else if ("/bye".equals(message)){
                        //채팅방안이라면? 채팅방에서 빠져나가고, 접속도 종료.
                        if(chatRoom != null){
                            chatRoom.broadcast(client.getNickname() + "님이 방을 나갔습니다.");
                            chatRoom.removeClient(client);
                        }

                        System.out.println(client.getNickname() + "닉네임의 사용자가 접속을 종료했습니다.");
                        clientManager.removeClient(client);
                        client.close();
                        break;
                    }

                }else{
                    //대화 입력이 들어왔을 때.
                    //지정된 채팅룸이 없을 때 대화 할 수 없었음.
                    if(chatRoom != null){
                        //정해진 대화방에 입장한 상태.
                        //정해진 대화방에 들어간 모든 사용자에게 메시지 전달.
                        chatRoom.broadcast(client.getNickname() + " : " + message);
                    }else{
                        System.out.println("채팅룸안에 있지 않아요.");
                        client.getOut().println("채팅룸안에 없으므로 대화 할 수 없어요.");
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
                client.close();
            }
        }

    }
}
