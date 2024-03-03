package NS.pgmg.controller;

import NS.pgmg.dto.BasicUserSignUpDto;
import NS.pgmg.dto.ModelUserSignUpDto;
import NS.pgmg.dto.ProPhotoUserSignUpDto;
import NS.pgmg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserRegisterController {

    private final UserService userService;

    @PostMapping("/basic-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> basicUserRegister(
            @RequestBody BasicUserSignUpDto basicUserSignUpDto
            ) {
        return userService.createBasicUser(basicUserSignUpDto);
    }

    @PostMapping("/model-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> ModelUserRegister(
            @RequestBody ModelUserSignUpDto modelUserSignUpDto
            ) {
        return userService.createModelUser(modelUserSignUpDto);
    }

    @PostMapping("/prophoto-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> proPhotoRegister(
            @RequestBody ProPhotoUserSignUpDto proPhotoUserSignUpDto
            ) {
        return userService.createProPhotoUser(proPhotoUserSignUpDto);
    }

    @PostMapping("/social-register")
    @ResponseStatus(HttpStatus.CREATED)
    public String socialRegister() {
        return "소셜 회원가입이 완료되었습니다.";
    }
}
