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

@RestController
@RequiredArgsConstructor
@RequestMapping("/duplicate")
public class DuplicateController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/email")
    public ResponseEntity<String> duplicateEmailCheck(
            @RequestBody DuplicateEmailDto duplicateEmailDto) {
        try {
            userRegisterService.basicUserEmailDuplicateCheck(duplicateEmailDto.getEmail());
            return new ResponseEntity<>("사용 가능한 이메일입니다.", HttpStatus.OK);
        } catch (EmailDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/name")
    public ResponseEntity<String> duplicateNameCheck(
            @RequestBody DuplicateNameDto duplicateNameDto) {
        try {
            userRegisterService.basicUserNameDuplicateCheck(duplicateNameDto.getName());
            return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
        } catch (NameDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
