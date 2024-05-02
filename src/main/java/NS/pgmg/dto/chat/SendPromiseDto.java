package NS.pgmg.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendPromiseDto {
    private String senderEmail;
    private String receiverEmail;
    private String content;
    private String date;
    private String place;
    private Integer price;
}
