package NS.pgmg.controller.board;

import NS.pgmg.domain.board.ModelBoard;
import NS.pgmg.dto.board.BoardRequestDto;
import NS.pgmg.dto.board.CreateModelDto;
import NS.pgmg.dto.board.UpdateModelDto;
import NS.pgmg.service.board.ModelAssistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/model/assistance")
public class ModelAssistanceController {

    private final ModelAssistanceService modelAssistanceService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> create(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") CreateModelDto createModelDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return modelAssistanceService.create(token, createModelDto, title, details);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> find(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return modelAssistanceService.find(boardRequestDto);
    }

    @GetMapping
    public List<ModelBoard> findAll() {
        return modelAssistanceService.findAll();
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> update(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody", required = false) UpdateModelDto updateModelDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return modelAssistanceService.update(token, updateModelDto, title, details);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> delete(
            @RequestHeader(value = "Token") String token,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return modelAssistanceService.delete(token, boardRequestDto);
    }
}
