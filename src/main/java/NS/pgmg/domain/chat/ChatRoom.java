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
    private String senderEmail;
    private String receiverEmail;
    private String lastMessage;

    @Builder
    public ChatRoom(String roomId, String senderEmail, String receiverEmail, String lastMessage) {
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.lastMessage = lastMessage;
    }

    public void updateLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
