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
public class Message {

    @Id
    private String id;
    private ChatType type;
    private String roomId;
    private String senderEmail;
    private String receiverEmail;
    private String content;
    private String date;
    private String place;
    private Integer price;
    private LocalDateTime createdAt;

    @Builder
    public Message(ChatType type, String roomId, String senderEmail, String receiverEmail, String content, String date, String place,
                   Integer price) {
        this.type = type;
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.date = date;
        this.place = place;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }
}
