package NS.pgmg.controller.user;

import NS.pgmg.dto.userpage.FindByNameDto;
import NS.pgmg.service.user.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "Favorite", description = "즐겨찾기 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 추가")
    @PostMapping
    public ResponseEntity<Map<String, String>> add(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindByNameDto findByNameDto
    ) {
        log.info("Call FavoriteController.add");
        return favoriteService.add(token, findByNameDto);
    }

    @Operation(summary = "즐겨찾기 삭제")
    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindByNameDto findByNameDto
    ) {
        log.info("Call FavoriteController.delete");
        return favoriteService.delete(token, findByNameDto);
    }

    @Operation(summary = "즐겨찾기 전체 조회")
    @PostMapping("/all")
    public ResponseEntity<?> findAll(
            @RequestHeader(value = "Token") String token
    ) {
        log.info("Call FavoriteController.findAll");
        return favoriteService.findAll(token);
    }
}
