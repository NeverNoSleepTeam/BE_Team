package NS.pgmg.dto.chat;

import NS.pgmg.domain.user.enums.UserRank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindAllRoomResponseDto {
    private String roomId;
    private String senderEmail;
    private String receiverEmail;
    private String receiverName;
    private UserRank receiverRank;
    private String receiverImgURL;
    private String lastMessage;

    @Builder
    public FindAllRoomResponseDto(String roomId, String receiverEmail, String senderEmail, String receiverName,
                                  UserRank receiverRank, String receiverImgURL, String lastMessage) {
        this.roomId = roomId;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.receiverName = receiverName;
        this.receiverRank = receiverRank;
        this.receiverImgURL = receiverImgURL;
        this.lastMessage = lastMessage;
    }
}
