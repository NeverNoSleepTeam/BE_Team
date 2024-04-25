package NS.pgmg.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindRoomDto {
    private String email;
    private String roomId;
    private String sender;
}
