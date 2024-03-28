package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindProPhotoInfoResponseDto {
    private String email;
    private String name;
    private Gender gender;
    private City city;
    private Nationality nationality;
    private String intro;
    private UserRank userRank;
    private List<String> profileImgPath;
    private BusinessTrip businessTrip;
    private Correction correction;
    private Production production;
    private String portfolioPath;
    private boolean isSelf;

    @Builder
    public FindProPhotoInfoResponseDto(String email, String name, Gender gender, City city, Nationality nationality,
                                       String intro, UserRank userRank, List<String> profileImgPath,
                                       BusinessTrip businessTrip, Correction correction, Production production,
                                       String portfolioPath, boolean isSelf) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.city = city;
        this.nationality = nationality;
        this.intro = intro;
        this.userRank = userRank;
        this.profileImgPath = profileImgPath;
        this.businessTrip = businessTrip;
        this.correction = correction;
        this.production = production;
        this.portfolioPath = portfolioPath;
        this.isSelf = isSelf;
    }
}
