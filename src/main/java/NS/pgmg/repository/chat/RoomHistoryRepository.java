package NS.pgmg.repository.chat;

import NS.pgmg.domain.chat.RoomHistory;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoomHistoryRepository extends MongoRepository<RoomHistory, String> {
    public RoomHistory findBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);
}
