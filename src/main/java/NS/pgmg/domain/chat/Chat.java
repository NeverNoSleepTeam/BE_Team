package NS.pgmg.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collation = "chat")
public class Chat {

    @Id
    private String id;
    private ChatType type;
    private String roomId;
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public Chat(ChatType type, String roomId, String sender, String receiver, String content) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
