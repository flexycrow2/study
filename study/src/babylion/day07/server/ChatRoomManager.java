package babylion.day07.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomManager {
    private static int idGenerator = 1;
    //ChatRoom List 를 저장해야하는데. 이 때는 어떤 자료 구조로 저장해야 할까.
    private Map<Integer, ChatRoom> map = new ConcurrentHashMap<>();

    //기능
    //ChatRoom 생성 -- 방번호가 자동 생성.
    public ChatRoom createChatRoom(){
        ChatRoom chatRoom = new ChatRoom(idGenerator++);
        map.put(chatRoom.getId(), chatRoom);
        return chatRoom;
    }

    //ChatRoom 삭제
    public void removeChatRoom(int id){
        map.remove(id);
    }

    //방번호에 해당하는 채팅룸 get하는 메서드
    public ChatRoom getChatRoom(int id){
        return map.get(id);
    }

    //채팅방 리스트 얻어오는.
    public List<ChatRoom> getChatRooms(){
        return new ArrayList<>(map.values());
    }

}
