package NS.pgmg.dto.chat;

import NS.pgmg.dto.chat.enums.MessageType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChatMessage {
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
