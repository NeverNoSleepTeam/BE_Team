package NS.pgmg.dto;

import NS.pgmg.domain.user.enums.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BasicUserSignUpDto {
    private final String email;
    private final String passwd;
    private final String passwd2;
    private final String name;
    private final Gender gender;
    private final String intro;

    @Builder
    public BasicUserSignUpDto(String email, String passwd, String passwd2, String name, Gender gender, String intro) {
        this.email = email;
        this.passwd = passwd;
        this.passwd2 = passwd2;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
    }
}
