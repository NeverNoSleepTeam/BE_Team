package NS.pgmg.dto.board;

import NS.pgmg.domain.board.enums.ModelCategory;
import NS.pgmg.domain.user.enums.UserRank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FindModelNeedResponseDto {
    private String name;
    private String title;
    private String contents;
    private Integer price;
    private String place;
    private List<ModelCategory> modelCategory;
    private LocalDateTime createdAt;
    private String firstDate;
    private String lastDate;
    private String titlePath;
    private List<String> detailPath;
    private UserRank userRank;

    @Builder
    public FindModelNeedResponseDto(String name, String title, String contents, Integer price, String place,
                                    List<ModelCategory> modelCategory, LocalDateTime createdAt, String firstDate,
                                    String lastDate, String titlePath, List<String> detailPath, UserRank userRank) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.place = place;
        this.modelCategory = modelCategory;
        this.createdAt = createdAt;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.titlePath = titlePath;
        this.detailPath = detailPath;
        this.userRank = userRank;
    }
}
