package NS.pgmg.dto;

import NS.pgmg.domain.user.enums.BusinessTrip;
import NS.pgmg.domain.user.enums.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProPhotoUserSignUpDto {
    private String email;
    private String name;
    private Gender gender;
    private String intro;
    private BusinessTrip businessTrip;
    private String portfolioURL;

    @Builder

    public ProPhotoUserSignUpDto(String email, String name, Gender gender, String intro,
                                 BusinessTrip businessTrip, String portfolioURL) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.intro = intro;
        this.businessTrip = businessTrip;
        this.portfolioURL = portfolioURL;
    }
}
