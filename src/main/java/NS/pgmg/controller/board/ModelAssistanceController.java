package NS.pgmg.controller.board;

import NS.pgmg.domain.board.ModelBoard;
import NS.pgmg.dto.board.BoardRequestDto;
import NS.pgmg.dto.board.CreateModelDto;
import NS.pgmg.dto.board.UpdateModelDto;
import NS.pgmg.service.board.ModelAssistanceService;
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
@Tag(name = "Model Assistance Board", description = "모델 해줄게 게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board/model/assistance")
public class ModelAssistanceController {

    private final ModelAssistanceService modelAssistanceService;

    @Operation(summary = "게시판 생성")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") CreateModelDto createModelDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        log.info("Call ModelAssistanceController.create");
        return modelAssistanceService.create(token, createModelDto, title, details);
    }

    @Operation(summary = "게시판 상세 조회")
    @PostMapping("/detail")
    public ResponseEntity<?> find(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        log.info("Call ModelAssistanceController.find");
        return modelAssistanceService.find(boardRequestDto);
    }

    @Operation(summary = "게시판 전체 조회")
    @GetMapping
    public List<ModelBoard> findAll() {
        log.info("Call ModelAssistanceController.findAll");
        return modelAssistanceService.findAll();
    }

    @Operation(summary = "게시판 수정")
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> update(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody", required = false) UpdateModelDto updateModelDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        log.info("Call ModelAssistanceController.update");
        return modelAssistanceService.update(token, updateModelDto, title, details);
    }

    @Operation(summary = "게시판 삭제")
    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        log.info("Call ModelAssistanceController.delete");
        return modelAssistanceService.delete(token, boardRequestDto);
    }
}
