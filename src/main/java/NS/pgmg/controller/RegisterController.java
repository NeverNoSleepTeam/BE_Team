package NS.pgmg.controller;

import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
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
            @RequestBody BasicRegisterDto basicRegisterDto
            ) {
        return userRegisterService.createBasicUser(basicRegisterDto);
    }

    @PostMapping("/model-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> ModelUserRegister(
            @RequestBody ModelRegisterDto modelRegisterDto
            ) {
        return userRegisterService.createModelUser(modelRegisterDto);
    }

    @PostMapping("/prophoto-register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> proPhotoRegister(
            @RequestBody ProPhotoRegisterDto proPhotoRegisterDto
            ) {
        return userRegisterService.createProPhotoUser(proPhotoRegisterDto);
    }
}
