package NS.pgmg.controller;

import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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

    @PostMapping(value = "/prophoto-register",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> proPhotoRegister(
            @RequestPart(value = "Request Body") ProPhotoRegisterDto proPhotoRegisterDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return userRegisterService.createProPhotoUser(proPhotoRegisterDto, file);
    }
}
