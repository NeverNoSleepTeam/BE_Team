package NS.pgmg.repository.chat;

import NS.pgmg.domain.chat.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<ChatRoom, String> {
    public ChatRoom findBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);
    public List<ChatRoom> findAllBySenderEmail(String senderEmail);
    public void deleteByRoomIdAndSenderEmail(String roomId, String senderId);
}
