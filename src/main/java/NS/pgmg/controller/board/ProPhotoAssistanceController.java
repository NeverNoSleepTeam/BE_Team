package NS.pgmg.controller.board;

import NS.pgmg.domain.board.ProPhotoBoard;
import NS.pgmg.dto.board.*;
import NS.pgmg.service.board.ProPhotoAssistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/pro-photo/assistance")
public class ProPhotoAssistanceController {

    private final ProPhotoAssistanceService proPhotoAssistanceService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") CreateProPhotoDto createProPhotoDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return proPhotoAssistanceService.create(token, createProPhotoDto, title, details);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> find(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return proPhotoAssistanceService.find(boardRequestDto);
    }

    @GetMapping
    public List<ProPhotoBoard> findAll() {
        return proPhotoAssistanceService.findAll();
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> update(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody", required = false) UpdateProPhotoDto updateProPhotoDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return proPhotoAssistanceService.update(token, updateProPhotoDto, title, details);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return proPhotoAssistanceService.delete(token, boardRequestDto);
    }
}
