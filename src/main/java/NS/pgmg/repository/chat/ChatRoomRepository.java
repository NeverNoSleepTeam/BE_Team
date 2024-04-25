package NS.pgmg.repository.chat;

import NS.pgmg.domain.chat.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    public List<ChatRoom> findAllBySender(String chatId);
    public ChatRoom findByRoomIdAndSender(String roomId, String sender);
    public void deleteByRoomIdAndSender(String roomId, String sender);
}
