package NS.pgmg.dto.board;

import NS.pgmg.domain.board.ModelCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateModelDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    private Integer price;
    private String place;
    private List<ModelCategory> modelCategory;
    private String firstDate;
    private String lastDate;

    @Builder
    public UpdateModelDto(String email, String title, String contents, Integer price, String place,
                          List<ModelCategory> modelCategory, String firstDate, String lastDate) {
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.place = place;
        this.modelCategory = modelCategory;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }
}
