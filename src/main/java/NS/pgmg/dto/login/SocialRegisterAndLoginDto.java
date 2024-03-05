package NS.pgmg.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialRegisterAndLoginDto {
    private String email;

    @Builder
    public SocialRegisterAndLoginDto(String email) {
        this.email = email;
    }
}
