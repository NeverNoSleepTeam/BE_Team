package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import lombok.Getter;

@Getter
public class UpdateBasicInfoRequestDto {
    private String email;
    private String passwd;
    private Gender gender;
    private Nationality nationality;
    private City city;
    private String intro;
}
