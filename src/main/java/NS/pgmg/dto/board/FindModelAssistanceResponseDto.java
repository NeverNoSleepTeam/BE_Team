package NS.pgmg.dto.board;

import NS.pgmg.domain.board.ModelCategory;
import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import NS.pgmg.domain.user.enums.UserRank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FindModelAssistanceResponseDto {
    private String name;
    private String title;
    private String contents;
    private Integer price;
    private String place;
    private List<ModelCategory> modelCategory;
    private LocalDateTime createdAt;
    private String firstDate;
    private String lastDate;
    private Gender gender;
    private String height;
    private String weight;
    private String top;
    private String bottom;
    private String shoes;
    private Nationality nationality;
    private City city;
    private String titlePath;
    private List<String> detailPath;
    private UserRank userRank;

    @Builder
    public FindModelAssistanceResponseDto(String name, String title, String contents, Integer price, String place,
                                          List<ModelCategory> modelCategory, LocalDateTime createdAt, String firstDate,
                                          String lastDate, Gender gender, String height, String weight, String top,
                                          String bottom, String shoes, Nationality nationality, City city,
                                          String titlePath, List<String> detailPath, UserRank userRank) {
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.price = price;
        this.place = place;
        this.modelCategory = modelCategory;
        this.createdAt = createdAt;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.nationality = nationality;
        this.city = city;
        this.titlePath = titlePath;
        this.detailPath = detailPath;
        this.userRank = userRank;
    }
}
