package NS.pgmg.controller.board;

import NS.pgmg.domain.board.PhotoShopBoard;
import NS.pgmg.dto.board.*;
import NS.pgmg.service.board.PhotoShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "PhotoShop Board", description = "포토샵 게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board/photo-shop")
public class PhotoShopController {

    private final PhotoShopService photoShopService;

    @Operation(summary = "게시판 생성")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") CreatePhotoShopDto createPhotoShopDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        log.info("Call PhotoShopController.create");
        return photoShopService.create(token, createPhotoShopDto, title, details);
    }

    @Operation(summary = "게시판 상세 조회")
    @PostMapping("/detail")
    public ResponseEntity<?> find(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        log.info("Call PhotoShopController.find");
        return photoShopService.find(boardRequestDto);
    }

    @Operation(summary = "게시판 전체 조회")
    @GetMapping
    public List<PhotoShopBoard> findAll() {
        log.info("Call PhotoShopController.findAll");
        return photoShopService.findAll();
    }

    @Operation(summary = "게시판 수정")
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> update(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody", required = false) UpdatePhotoShopDto updatePhotoShopDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        log.info("Call PhotoShopController.update");
        return photoShopService.update(token, updatePhotoShopDto, title, details);
    }

    @Operation(summary = "게시판 삭제")
    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        log.info("Call PhotoShopController.delete");
        return photoShopService.delete(token, boardRequestDto);
    }
}
