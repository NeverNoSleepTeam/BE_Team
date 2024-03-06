package NS.pgmg.controller;

import NS.pgmg.dto.duplicate.DuplicateEmailDto;
import NS.pgmg.dto.duplicate.DuplicateNameDto;
import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/duplicate")
public class DuplicateController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> duplicateEmailCheck(
            @RequestBody DuplicateEmailDto duplicateEmailDto
    ) {
        try {
            userRegisterService.basicUserEmailDuplicateCheck(duplicateEmailDto.getEmail());
            return ResponseEntity.ok().body(Map.of("message", "사용가능한 이메일입니다."));
        } catch (EmailDuplicateException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));

        }
    }

    @PostMapping("/name")
    public ResponseEntity<Map<String, String>> duplicateNameCheck(
            @RequestBody DuplicateNameDto duplicateNameDto) {
        try {
            userRegisterService.basicUserNameDuplicateCheck(duplicateNameDto.getName());
            return ResponseEntity.ok().body(Map.of("message", "사용가능한 닉네임입니다."));
        } catch (NameDuplicateException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));
        }
    }

}
