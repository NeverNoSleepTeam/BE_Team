package NS.pgmg.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@NoArgsConstructor
@Document(collection = "history")
public class RoomHistory {

    @Id
    private String id;
    private String roomId;
    private String senderEmail;
    private String receiverEmail;

    @Builder

    public RoomHistory(String roomId, String senderEmail, String receiverEmail) {
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
    }
}
