package NS.pgmg.controller.board;

import NS.pgmg.domain.board.PhotoShopBoard;
import NS.pgmg.dto.board.*;
import NS.pgmg.service.board.PhotoShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/photo-shop")
public class PhotoShopController {

    private final PhotoShopService photoShopService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") CreatePhotoShopDto createPhotoShopDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return photoShopService.create(token, createPhotoShopDto, title, details);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> find(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return photoShopService.find(boardRequestDto);
    }

    @GetMapping
    public List<PhotoShopBoard> findAll() {
        return photoShopService.findAll();
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> update(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody", required = false) UpdatePhotoShopDto updatePhotoShopDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return photoShopService.update(token, updatePhotoShopDto, title, details);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return photoShopService.delete(token, boardRequestDto);
    }
}
