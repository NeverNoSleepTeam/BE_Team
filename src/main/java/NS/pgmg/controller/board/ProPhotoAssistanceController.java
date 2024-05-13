package NS.pgmg.controller.board;

import NS.pgmg.domain.board.ProPhotoBoard;
import NS.pgmg.dto.board.*;
import NS.pgmg.service.board.ProPhotoAssistanceService;
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
@Tag(name = "ProPhoto Assistance Board", description = "사진기사 해줄게 게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board/pro-photo/assistance")
public class ProPhotoAssistanceController {

    private final ProPhotoAssistanceService proPhotoAssistanceService;

    @Operation(summary = "게시판 생성")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") CreateProPhotoDto createProPhotoDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        log.info("Call ProPhotoAssistanceController.create");
        return proPhotoAssistanceService.create(token, createProPhotoDto, title, details);
    }

    @Operation(summary = "게시판 상세 조회")
    @PostMapping("/detail")
    public ResponseEntity<?> find(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        log.info("Call ProPhotoAssistanceController.find");
        return proPhotoAssistanceService.find(boardRequestDto);
    }

    @Operation(summary = "게시판 전체 조회")
    @GetMapping
    public List<ProPhotoBoard> findAll() {
        log.info("Call ProPhotoAssistanceController.findAll");
        return proPhotoAssistanceService.findAll();
    }

    @Operation(summary = "게시판 수정")
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> update(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody", required = false) UpdateProPhotoDto updateProPhotoDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        log.info("Call ProPhotoAssistanceController.update");
        return proPhotoAssistanceService.update(token, updateProPhotoDto, title, details);
    }

    @Operation(summary = "게시판 삭제")
    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        log.info("Call ProPhotoAssistanceController.delete");
        return proPhotoAssistanceService.delete(token, boardRequestDto);
    }
}
