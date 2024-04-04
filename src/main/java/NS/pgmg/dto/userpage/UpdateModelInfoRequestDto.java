package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateModelInfoRequestDto {
    private String email;
    private Gender gender;
    private Nationality nationality;
    private City city;
    private String intro;
    private String height;
    private String weight;
    private String top;
    private String bottom;
    private String shoes;
}
