package NS.pgmg.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FindTalentDonationResponseDto {
    private String name;
    private String title;
    private String contents;
    private String place;
    private LocalDateTime createdAt;
    private String firstDate;
    private String lastDate;
    private String titlePath;
    private List<String> detailPath;

    @Builder
    public FindTalentDonationResponseDto(String name, String title, String contents, String place,
                                         LocalDateTime createdAt, String firstDate, String lastDate, String titlePath,
                                         List<String> detailPath) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.place = place;
        this.createdAt = createdAt;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.titlePath = titlePath;
        this.detailPath = detailPath;
    }
}
