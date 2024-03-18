package NS.pgmg.dto.userpage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindUserPageRequestDto {

    private String name;

    @Builder
    public FindUserPageRequestDto(String name) {
        this.name = name;
    }
}
