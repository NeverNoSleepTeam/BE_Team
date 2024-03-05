package NS.pgmg.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DuplicateNameDto {
    private String name;

    @Builder
    public DuplicateNameDto(String name) {
        this.name = name;
    }
}
