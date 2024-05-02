package NS.pgmg.repository.chat;

import NS.pgmg.domain.chat.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    public List<Message> findAllByRoomIdOrderByCreatedAtDesc(String roomId);
}
