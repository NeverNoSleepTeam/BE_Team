package NS.pgmg.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String passwd;

    @Builder
    public LoginDto(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }
}
