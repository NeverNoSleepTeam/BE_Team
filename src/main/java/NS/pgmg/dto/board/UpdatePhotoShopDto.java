package NS.pgmg.dto.board;

import NS.pgmg.domain.board.PhotoShopCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdatePhotoShopDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    private Integer price;
    private List<PhotoShopCategory> photoShopCategory;
    private String firstDate;
    private String lastDate;

    @Builder
    public UpdatePhotoShopDto(String email, String title, String contents, Integer price,
                              List<PhotoShopCategory> photoShopCategory, String firstDate, String lastDate) {
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.photoShopCategory = photoShopCategory;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }
}
