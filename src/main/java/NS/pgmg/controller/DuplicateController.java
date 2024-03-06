package NS.pgmg.controller;

import NS.pgmg.dto.duplicate.DuplicateEmailDto;
import NS.pgmg.dto.duplicate.DuplicateNameDto;
import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/duplicate")
public class DuplicateController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/email")
    public Map<HttpStatus, String> duplicateEmailCheck(
            @RequestBody DuplicateEmailDto duplicateEmailDto
    ) {
        try {
            userRegisterService.basicUserEmailDuplicateCheck(duplicateEmailDto.getEmail());
            return Map.of(HttpStatus.OK, "사용가능한 이메일입니다.");
        } catch (EmailDuplicateException e) {
            return Map.of(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/name")
    public Map<HttpStatus, String> duplicateNameCheck(
            @RequestBody DuplicateNameDto duplicateNameDto) {
        try {
            userRegisterService.basicUserNameDuplicateCheck(duplicateNameDto.getName());
            return Map.of(HttpStatus.OK, "사용가능한 닉네임입니다.");
        } catch (NameDuplicateException e) {
            return Map.of(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
