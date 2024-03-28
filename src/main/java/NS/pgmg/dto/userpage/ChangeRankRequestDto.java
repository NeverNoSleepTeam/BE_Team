package NS.pgmg.dto.userpage;

import NS.pgmg.domain.user.enums.UserRank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeRankRequestDto {
    String email;
    UserRank userRank;
}
