package NS.pgmg.dto.board;

import NS.pgmg.domain.board.PhotoShopCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FindPhotoShopResponseDto {
    private String name;
    private String title;
    private String contents;
    private Integer price;
    private List<PhotoShopCategory> photoShopCategory;
    private LocalDateTime createdAt;
    private String firstDate;
    private String lastDate;
    private String titlePath;
    private List<String> detailPath;

    @Builder
    public FindPhotoShopResponseDto(String name, String title, String contents, Integer price,
                                    List<PhotoShopCategory> photoShopCategory, LocalDateTime createdAt,
                                    String firstDate, String lastDate, String titlePath, List<String> detailPath) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.photoShopCategory = photoShopCategory;
        this.createdAt = createdAt;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.titlePath = titlePath;
        this.detailPath = detailPath;
    }
}
