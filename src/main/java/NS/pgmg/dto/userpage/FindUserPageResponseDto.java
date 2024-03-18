package NS.pgmg.dto.userpage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindUserPageResponseDto {

    private String email;
    private String name;
    private String intro;
    private String baseUser;

    @Builder
    public FindUserPageResponseDto(String email, String name, String intro, String baseUser) {
        this.email = email;
        this.name = name;
        this.intro = intro;
        this.baseUser = baseUser;
    }
}
