package NS.pgmg.dto.register;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasicRegisterDto {
    private String email;
    private String passwd;
    private String passwd2;
    private String name;
    private Gender gender;
    private City city;
    private Nationality nationality;
    private String intro;

    @Builder
    public BasicRegisterDto(String email, String passwd, String passwd2, String name, Gender gender, City city,
                            Nationality nationality, String intro) {
        this.email = email;
        this.passwd = passwd;
        this.passwd2 = passwd2;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.nationality = nationality;
        this.intro = intro;
    }
}
