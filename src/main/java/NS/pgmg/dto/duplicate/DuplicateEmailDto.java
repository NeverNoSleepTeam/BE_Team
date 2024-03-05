package NS.pgmg.dto.duplicate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DuplicateEmailDto {
    private String email;

    @Builder
    public DuplicateEmailDto(String email) {
        this.email = email;
    }
}
