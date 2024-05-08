package NS.pgmg.dto.board;

import NS.pgmg.domain.board.enums.ModelCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateModelDto {
    private String email;
    private String title;
    private String contents;
    private Integer price;
    private String place;
    private List<ModelCategory> category;
    private String firstDate;
    private String lastDate;

    @Builder
    public CreateModelDto(String email, String title, String contents, Integer price, String place,
                          List<ModelCategory> category, String firstDate, String lastDate) {
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.place = place;
        this.category = category;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }
}
