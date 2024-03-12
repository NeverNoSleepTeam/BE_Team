package NS.pgmg.dto.register;

import NS.pgmg.domain.user.enums.City;
import NS.pgmg.domain.user.enums.Nationality;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModelRegisterDto {
    private String email;
    private String height;
    private String weight;
    private String top;
    private String bottom;
    private String shoes;

    @Builder
    public ModelRegisterDto(String email, String height, String weight, String top, String bottom, String shoes) {
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
    }
}
