package NS.pgmg.dto.board;

import NS.pgmg.domain.board.enums.ProPhotoCategory;
import NS.pgmg.domain.user.enums.UserRank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FindProPhotoNeedResponseDto {
    private String name;
    private String title;
    private String contents;
    private Integer price;
    private String place;
    private List<ProPhotoCategory> category;
    private LocalDateTime createdAt;
    private String firstDate;
    private String lastDate;
    private String titlePath;
    private List<String> detailPath;
    private UserRank userRank;

    @Builder
    public FindProPhotoNeedResponseDto(String name, String title, String contents, Integer price, String place,
                                       List<ProPhotoCategory> category, LocalDateTime createdAt, String firstDate,
                                       String lastDate, String titlePath, List<String> detailPath, UserRank userRank) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.place = place;
        this.category = category;
        this.createdAt = createdAt;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.titlePath = titlePath;
        this.detailPath = detailPath;
        this.userRank = userRank;
    }
}
