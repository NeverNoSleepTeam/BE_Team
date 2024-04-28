package NS.pgmg.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRoomDto {
    private String senderEmail;
    private String receiverName;
}
