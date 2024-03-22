package NS.pgmg.dto.userpage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindModelInfoResponseDto {
    private String email;
    private String name;
    private String height;
    private String weight;
    private String top;
    private String bottom;
    private String shoes;

    @Builder
    public FindModelInfoResponseDto(String email, String name, String height, String weight, String top, String bottom,
                                    String shoes) {
        this.email = email;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
    }
}
