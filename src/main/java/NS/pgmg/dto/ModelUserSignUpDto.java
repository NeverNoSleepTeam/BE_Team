package NS.pgmg.dto;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ModelUserSignUpDto {
    private final String email;
    private final Gender gender;
    private final String intro;
    private final String height;
    private final String weight;
    private final String top;
    private final String bottom;
    private final String shoes;
    private final Nationality nationality;
    private final City city;

    @Builder
    public ModelUserSignUpDto(String email, Gender gender, String intro, String height, String weight,
                              String top, String bottom, String shoes, Nationality nationality, City city) {
        this.email = email;
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
