package NS.pgmg.repository.chat;

import NS.pgmg.domain.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    public List<Chat> findAllByRoomId(String roomId);
}
