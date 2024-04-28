package NS.pgmg.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatViewDto {

    private String roomId;
    private String senderEmail;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public ChatViewDto(String roomId, String senderEmail, String content, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.content = content;
        this.createdAt = createdAt;
    }
}
