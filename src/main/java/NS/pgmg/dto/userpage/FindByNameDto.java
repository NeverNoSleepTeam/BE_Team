package NS.pgmg.dto.userpage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindByNameDto {

    private String name;

    @Builder
    public FindByNameDto(String name) {
        this.name = name;
    }
}
