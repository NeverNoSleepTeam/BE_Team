package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.BusinessTrip;
import NS.pgmg.domain.user.enums.Correction;
import NS.pgmg.domain.user.enums.Production;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindProPhotoInfoResponseDto {
    private String email;
    private String name;
    private BusinessTrip businessTrip;
    private Correction correction;
    private Production production;
    private String portfolioPath;

    @Builder
    public FindProPhotoInfoResponseDto(String email, String name, BusinessTrip businessTrip, Correction correction,
                                       Production production, String portfolioPath) {
        this.email = email;
        this.name = name;
        this.businessTrip = businessTrip;
        this.correction = correction;
        this.production = production;
        this.portfolioPath = portfolioPath;
    }
}
