package NS.pgmg.dto.board;

import NS.pgmg.domain.board.enums.ProPhotoCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateProPhotoDto {
    private Long id;
    private String email;
    private String title;
    private String contents;
    private Integer price;
    private String place;
    private List<ProPhotoCategory> proPhotoCategory;
    private String firstDate;
    private String lastDate;

    @Builder
    public UpdateProPhotoDto(String email, String title, String contents, Integer price, String place,
                             List<ProPhotoCategory> proPhotoCategory, String firstDate, String lastDate) {
        this.email = email;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.place = place;
        this.proPhotoCategory = proPhotoCategory;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }
}
