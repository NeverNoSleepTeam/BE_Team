package NS.pgmg.controller;

import NS.pgmg.dto.userpage.FindByNameDto;
import NS.pgmg.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Map<String, String>> add(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindByNameDto findByNameDto
    ) {
        return favoriteService.add(token, findByNameDto);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindByNameDto findByNameDto
    ) {
        return favoriteService.delete(token, findByNameDto);
    }

    @PostMapping("/all")
    public ResponseEntity<?> findAll(
            @RequestHeader(value = "Token") String token
    ) {
        return favoriteService.findAll(token);
    }
}
