package NS.pgmg.dto.board;

import NS.pgmg.domain.board.ModelAssistanceCategory;
import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ModelAssistanceFindResponseDto {
    private String name;
    private LocalDateTime createdAt;
    private String title;
    private Integer price;
    private List<ModelAssistanceCategory> modelAssistanceCategory;
    private String firstDate;
    private String lastDate;
    private String place;
    private Gender gender;
    private String height;
    private String weight;
    private String top;
    private String bottom;
    private String shoes;
    private Nationality nationality;
    private City city;

    @Builder

    public ModelAssistanceFindResponseDto(String name, LocalDateTime createdAt, String title, Integer price,
                                          List<ModelAssistanceCategory> modelAssistanceCategory, String firstDate, String lastDate,
                                          String place, Gender gender, String height, String weight, String top,
                                          String bottom, String shoes, Nationality nationality, City city) {
        this.name = name;
        this.createdAt = createdAt;
        this.title = title;
        this.price = price;
        this.modelAssistanceCategory = modelAssistanceCategory;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.place = place;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.nationality = nationality;
        this.city = city;
    }
}
