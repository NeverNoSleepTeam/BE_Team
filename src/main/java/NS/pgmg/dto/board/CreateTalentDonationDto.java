package NS.pgmg.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CreateTalentDonationDto {
    private String email;
    private String title;
    private String contents;
    private String place;
    private String firstDate;
    private String lastDate;

    @Builder
    public CreateTalentDonationDto(String email, String title, String contents, String place,
                                   String firstDate, String lastDate) {
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.place = place;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }
}
