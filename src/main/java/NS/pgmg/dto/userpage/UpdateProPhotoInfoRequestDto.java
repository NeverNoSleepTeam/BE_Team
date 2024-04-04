package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProPhotoInfoRequestDto {
    private String email;
    private Gender gender;
    private Nationality nationality;
    private City city;
    private String intro;
    private BusinessTrip businessTrip;
    private Correction correction;
    private Production production;
    private String URL;
}