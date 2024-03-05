package NS.pgmg.controller;

import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/duplicate")
public class DuplicateController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/email")
    public ResponseEntity<String> duplicateEmailCheck(
            @RequestParam(name = "email") String email) {
        try {
            userRegisterService.basicUserEmailDuplicateCheck(email);
            return new ResponseEntity<>("사용 가능한 이메일입니다.", HttpStatus.OK);
        } catch (EmailDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/name")
    public ResponseEntity<String> duplicateNameCheck(
            @RequestParam(name = "name") String name) {
        try {
            userRegisterService.basicUserNameDuplicateCheck(name);
            return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
        } catch (NameDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
