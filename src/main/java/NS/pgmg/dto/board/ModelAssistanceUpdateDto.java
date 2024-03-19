package NS.pgmg.dto.board;

import NS.pgmg.domain.board.ModelAssistanceCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ModelAssistanceUpdateDto {
    private Long id;
    private String email;
    private String title;
    private List<ModelAssistanceCategory> modelAssistanceCategory;
    private String firstDate;
    private String lastDate;
    private String contents;
    private Integer price;
    private String place;

    @Builder
    public ModelAssistanceUpdateDto(Long id, String email, String title, List<ModelAssistanceCategory> modelAssistanceCategory,
                                    String firstDate, String lastDate, String contents, Integer price, String place) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.modelAssistanceCategory = modelAssistanceCategory;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.contents = contents;
        this.price = price;
        this.place = place;
    }
}
