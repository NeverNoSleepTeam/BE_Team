package NS.pgmg.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "chat")
public class Chat {

    @Id
    private String id;
    private String roomId;
    private String senderEmail;
    private String receiverEmail;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public Chat(String roomId, String senderEmail, String receiverEmail, String content) {
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void updateEmail(String name) {
        this.receiverEmail = name;
    }
}
