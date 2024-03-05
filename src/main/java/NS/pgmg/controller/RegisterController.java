package NS.pgmg.controller;

import NS.pgmg.dto.BasicUserSignUpDto;
import NS.pgmg.dto.ModelUserSignUpDto;
import NS.pgmg.dto.ProPhotoUserSignUpDto;
import NS.pgmg.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class RegisterController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/basic-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> basicUserRegister(
            @RequestBody BasicUserSignUpDto basicUserSignUpDto
            ) {
        return userRegisterService.createBasicUser(basicUserSignUpDto);
    }

    @PostMapping("/model-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> ModelUserRegister(
            @RequestBody ModelUserSignUpDto modelUserSignUpDto
            ) {
        return userRegisterService.createModelUser(modelUserSignUpDto);
    }

    @PostMapping("/prophoto-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> proPhotoRegister(
            @RequestBody ProPhotoUserSignUpDto proPhotoUserSignUpDto
            ) {
        return userRegisterService.createProPhotoUser(proPhotoUserSignUpDto);
    }

    @PostMapping("/social-register")
    @ResponseStatus(HttpStatus.CREATED)
    public String socialRegister() {
        return "소셜 회원가입이 완료되었습니다.";
    }
}
