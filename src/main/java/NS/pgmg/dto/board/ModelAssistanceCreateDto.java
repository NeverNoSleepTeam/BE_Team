package NS.pgmg.dto.board;

import NS.pgmg.domain.board.ModelAssistanceCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ModelAssistanceCreateDto {
    private String email;
    private String title;
    private List<ModelAssistanceCategory> modelAssistanceCategory;
    private String place;
    private Integer price;
    private String contents;
    private String firstDate;
    private String lastDate;

    @Builder
    public ModelAssistanceCreateDto(String email, String title, List<ModelAssistanceCategory> modelAssistanceCategory, String place,
                                    Integer price, String contents, String firstDate, String lastDate) {
        this.email = email;
        this.title = title;
        this.modelAssistanceCategory = modelAssistanceCategory;
        this.place = place;
        this.price = price;
        this.contents = contents;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }
}
