package NS.pgmg.controller.user;

import NS.pgmg.dto.userpage.FindByEmailDto;
import NS.pgmg.service.user.RankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "Rank", description = "회원 등급 변경 관련 API")
@RestController
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @Operation(summary = "일반 등급")
    @PutMapping("/user/info/rank/basic")
    public ResponseEntity<Map<String, String>> transformBasic(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindByEmailDto findByEmailDto
    ) {
        log.info("Call RankController.transformBasic");
        return rankService.changeBasicRank(token, findByEmailDto);
    }

    @Operation(summary = "모델 등급")
    @PutMapping("/user/info/rank/model")
    public ResponseEntity<Map<String, String>> transformModel(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindByEmailDto findByEmailDto
    ) {
        log.info("Call RankController.transformModel");
        return rankService.changeModelRank(token, findByEmailDto);
    }

    @Operation(summary = "사진기사 등급")
    @PutMapping("/user/info/rank/pro-photo")
    public ResponseEntity<Map<String, String>> transformProPhoto(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindByEmailDto findByEmailDto
    ) {
        log.info("Call RankController.transformProPhoto");
        return rankService.changeProPhotoRank(token, findByEmailDto);
    }
}
