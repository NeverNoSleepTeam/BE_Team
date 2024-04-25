package NS.pgmg.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "room")
public class ChatRoom {

    @Id
    private String id;
    private String roomId;
    private String lastMessage;
    private String sender;
    private String receiver;

    @Builder
    public ChatRoom(String roomId, String lastMessage, String sender, String receiver) {
        this.roomId = roomId;
        this.lastMessage = lastMessage;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void updateLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
