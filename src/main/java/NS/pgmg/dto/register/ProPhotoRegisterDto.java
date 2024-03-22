package NS.pgmg.dto.register;

import NS.pgmg.domain.user.enums.BusinessTrip;
import NS.pgmg.domain.user.enums.Correction;
import NS.pgmg.domain.user.enums.Production;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProPhotoRegisterDto {
    private String email;
    private BusinessTrip businessTrip;
    private Correction correction;
    private Production production;

    @Builder
    public ProPhotoRegisterDto(String email, BusinessTrip businessTrip, Correction correction, Production production) {
        this.email = email;
        this.businessTrip = businessTrip;
        this.correction = correction;
        this.production = production;
    }
}
