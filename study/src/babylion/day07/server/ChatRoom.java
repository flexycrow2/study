package babylion.day07.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatRoom {
    //필드로 무엇이 필요할까
    //방번호
    //클라이언트 리스트
    private int id; //방번호
    private List<Client> clients = Collections.synchronizedList(new ArrayList<>());

    public ChatRoom(int id) {
        this.id = id;
    }

    //룸이 해야하는 기능.


    public int getId() {
        return id;
    }
    //유저추가
    public void addClient(Client client){
        clients.add(client);
    }
    //유저삭제
    public void removeClient(Client client){
        clients.remove(client);
    }
    //룸에 있는 사용자에게 채팅 메시지를 보내주는 메소드
    public void broadcast(String message){
        for (Client client : clients) {
            client.getOut().println(message);
        }
    }


}
