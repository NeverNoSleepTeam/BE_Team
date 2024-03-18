package NS.pgmg.controller;

import NS.pgmg.dto.board.ModelAssistanceCreateDto;
import NS.pgmg.service.BoardService;
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
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/model-assistance",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> createModelAssistanceBoard(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") ModelAssistanceCreateDto modelAssistanceCreateDto,
            @RequestPart(value = "Title", required = false) MultipartFile title,
            @RequestPart(value = "Details", required = false) List<MultipartFile> details
    ) {
        return boardService.create(token, modelAssistanceCreateDto, title, details);
    }

    @GetMapping("/model-assistance")
    public ResponseEntity<Map<String, String>> findModelAssistanceBoard() {
        return ResponseEntity.ok().body(Map.of("read", "modelAssistance"));
    }

    @PutMapping("/model-assistance")
    public ResponseEntity<Map<String, String>> updateModelAssistanceBoard() {
        return ResponseEntity.ok().body(Map.of("update", "modelAssistance"));
    }


    @DeleteMapping("/model-assistance")
    public ResponseEntity<Map<String, String>> deleteModelAssistanceBoard() {
        return ResponseEntity.ok().body(Map.of("delete", "modelAssistance"));
    }
}
