package NS.pgmg.dto.userpage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindUserPageResponseDto {

    private String email;
    private String name;
    private String intro;
    private String userInfo;
    private List<String> profileImgPath;

    @Builder
    public FindUserPageResponseDto(String email, String name, String intro,
                                   String userInfo, List<String> profileImgPath) {
        this.email = email;
        this.name = name;
        this.intro = intro;
        this.userInfo = userInfo;
        this.profileImgPath = profileImgPath;
    }
}
