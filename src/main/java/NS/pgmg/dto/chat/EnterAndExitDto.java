package NS.pgmg.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnterAndExitDto {
    private String email;
    private String sender;
    private String receiver;
}
