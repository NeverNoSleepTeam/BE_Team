package NS.pgmg.controller;

import NS.pgmg.dto.LoginDto;
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
        return userLoginService.basicUserLogin(loginDto);
    }

}
