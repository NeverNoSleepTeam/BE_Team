package NS.pgmg.controller.user;

import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.service.user.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "Login", description = "로그인 관련 API")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "일반 로그인")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> basicUserLogin(
            @RequestBody LoginDto loginDto
    ) {
        log.info("Call LoginController.basicUserLogin");
        log.info("email: {}, passwd: {}", loginDto.getEmail(), loginDto.getPasswd());
        return loginService.login(loginDto);
    }

    @Operation(summary = "소셜 로그인")
    @PostMapping("/social")
    public ResponseEntity<Map<String, String>> socialRegisterAndLogin(
            @RequestBody SocialRegisterAndLoginDto socialRegisterAndLoginDto
    ) {
        log.info("Call LoginController.socialRegisterAndLogin");
        return loginService.socialLogin(socialRegisterAndLoginDto);
    }
}
