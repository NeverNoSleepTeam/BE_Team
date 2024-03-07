package NS.pgmg.controller;

import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;

    @PostMapping("/basic-login")
    public ResponseEntity<String> basicUserLogin(
            @RequestBody LoginDto loginDto
    ) {
        return userLoginService.login(loginDto);
    }

    @PostMapping("/social-register-login")
    public ResponseEntity<String> socialRegisterAndLogin(
            @RequestBody SocialRegisterAndLoginDto socialRegisterAndLoginDto
    ) {
        return userLoginService.socialLogin(socialRegisterAndLoginDto);
    }
}
