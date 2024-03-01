package NS.pgmg.controller;

import NS.pgmg.dto.BasicUserSignUpDto;
import NS.pgmg.service.BasicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final BasicUserService basicUserService;

    @PostMapping("/auth/basic-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> basicUserRegister(
            @RequestBody BasicUserSignUpDto basicUserSignUpDto
            ) {
        return basicUserService.createBasicUser(basicUserSignUpDto);
    }

    @PostMapping("/auth/model-register")
    @ResponseStatus(HttpStatus.CREATED)
    public String ModelUserRegister() {
        return "모델 회원가입이 완료되었습니다.";
    }

    @PostMapping("/auth/prophoto-register")
    @ResponseStatus(HttpStatus.CREATED)
    public String proPhotoRegister() {
        return "사진기사 회원가입이 완료되었습니다.";
    }

    @PostMapping("/auth/social-register")
    @ResponseStatus(HttpStatus.CREATED)
    public String socialRegister() {
        return "소셜 회원가입이 완료되었습니다.";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login() {
        return "로그인되었습니다.";
    }
}
