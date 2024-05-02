package NS.pgmg.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendMessageDto {
    private String senderEmail;
    private String receiverEmail;
    private String content;
}
