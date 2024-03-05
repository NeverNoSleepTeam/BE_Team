package NS.pgmg.dto;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModelUserSignUpDto {
    private String email;
    private String name;
    private Gender gender;
    private String intro;
    private String height;
    private String weight;
    private String top;
    private String bottom;
    private String shoes;
    private Nationality nationality;
    private City city;

    @Builder
    public ModelUserSignUpDto(String email, String name, Gender gender, String intro, String height, String weight,
                              String top, String bottom, String shoes, Nationality nationality, City city) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
        this.height = height;
        this.weight = weight;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.nationality = nationality;
        this.city = city;
    }
}
