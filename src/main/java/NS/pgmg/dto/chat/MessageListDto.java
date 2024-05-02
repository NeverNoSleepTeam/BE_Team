package NS.pgmg.dto.chat;

import NS.pgmg.domain.chat.ChatType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageListDto {
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
}
