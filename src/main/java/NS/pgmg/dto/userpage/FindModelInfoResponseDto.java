package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Gender;
import NS.pgmg.domain.user.enums.Nationality;
import NS.pgmg.domain.user.enums.UserRank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindModelInfoResponseDto {
    private String email;
    private String name;
    private Gender gender;
    private City city;
    private Nationality nationality;
    private String intro;
    private UserRank userRank;
    private List<String> profileModelImgPath;
    private String height;
    private String weight;
    private String top;
    private String bottom;
    private String shoes;
    private boolean isSelf;

    @Builder
    public FindModelInfoResponseDto(String email, String name, Gender gender, City city, Nationality nationality,
                                    String intro, UserRank userRank, List<String> profileModelImgPath, String height,
                                    String weight, String top, String bottom, String shoes, boolean isSelf) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.nationality = nationality;
        this.intro = intro;
        this.userRank = userRank;
        this.profileModelImgPath = profileModelImgPath;
        this.height = height;
        this.weight = weight;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.isSelf = isSelf;
    }
}
