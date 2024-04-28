package NS.pgmg.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindAllRoomResponseDto {
    private String roomId;
    private String senderEmail;
    private String receiverName;
    private String lastMessage;

    @Builder
    public FindAllRoomResponseDto(String roomId, String senderEmail, String receiverName, String lastMessage) {
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.receiverName = receiverName;
        this.lastMessage = lastMessage;
    }
}
