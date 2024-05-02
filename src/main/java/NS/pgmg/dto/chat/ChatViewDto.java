package NS.pgmg.dto.chat;

import NS.pgmg.domain.chat.ChatType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatViewDto {
    private ChatType type;
    private String roomId;
    private String senderEmail;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public ChatViewDto(ChatType type, String roomId, String senderEmail, String content, LocalDateTime createdAt) {
        this.type = type;
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.content = content;
        this.createdAt = createdAt;
    }
}
