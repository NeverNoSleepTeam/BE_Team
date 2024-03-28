package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.Rank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeRankRequestDto {
    String email;
    Rank rank;
}
