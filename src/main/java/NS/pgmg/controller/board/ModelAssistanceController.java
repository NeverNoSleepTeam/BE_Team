package NS.pgmg.controller.board;

import NS.pgmg.domain.board.ModelAssistance;
import NS.pgmg.dto.board.BoardRequestDto;
import NS.pgmg.dto.board.ModelAssistanceCreateDto;
import NS.pgmg.dto.board.ModelAssistanceUpdateDto;
import NS.pgmg.service.board.ModelAssistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@ResponseStatus(HttpStatus.CREATED)
public class ModelAssistanceController {

    private final ModelAssistanceService modelAssistanceService;

    @PostMapping(value = "/model-assistance",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createModelAssistanceBoard(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") ModelAssistanceCreateDto modelAssistanceCreateDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return modelAssistanceService.createMA(token, modelAssistanceCreateDto, title, details);
    }

    @PostMapping("/model-assistance/detail")
    public ResponseEntity<?> findModelAssistanceBoard(
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return modelAssistanceService.findMA(boardRequestDto);
    }

    @GetMapping("/model-assistance")
    public List<ModelAssistance> findAllModelAssistanceBoard() {
        return modelAssistanceService.findAllMA();
    }

    @PutMapping(value = "/model-assistance",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> updateModelAssistanceBoard(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody", required = false) ModelAssistanceUpdateDto modelAssistanceUpdateDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return modelAssistanceService.updateMA(token, modelAssistanceUpdateDto, title, details);
    }

    @DeleteMapping("/model-assistance")
    public ResponseEntity<Map<String, String>> deleteModelAssistanceBoard(
            @RequestHeader(value = "Token") String token,
            @RequestBody BoardRequestDto boardRequestDto
    ) {
        return modelAssistanceService.deleteMA(token, boardRequestDto);
    }
}
