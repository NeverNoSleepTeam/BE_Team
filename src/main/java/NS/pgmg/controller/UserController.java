package NS.pgmg.controller;

import NS.pgmg.dto.duplicate.DuplicateEmailDto;
import NS.pgmg.dto.duplicate.DuplicateNameDto;
import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.dto.userpage.FindUserPageRequestDto;
import NS.pgmg.dto.userpage.FindUserPageResponseDto;
import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join/basic")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> basicUserRegister(
            @RequestBody BasicRegisterDto basicRegisterDto
    ) {
        return userService.createBasicUser(basicRegisterDto);
    }

    @PostMapping("/join/model")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> ModelUserRegister(
            @RequestBody ModelRegisterDto modelRegisterDto
    ) {
        return userService.createModelUser(modelRegisterDto);
    }

    @PostMapping(value = "/join/pro-photo",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> proPhotoRegister(
            @RequestPart(value = "RequestBody") ProPhotoRegisterDto proPhotoRegisterDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return userService.createProPhotoUser(proPhotoRegisterDto, file);
    }

    @PostMapping("/login")
    public ResponseEntity<String> basicUserLogin(
            @RequestBody LoginDto loginDto
    ) {
        return userService.login(loginDto);
    }

    @PostMapping("/social")
    public ResponseEntity<String> socialRegisterAndLogin(
            @RequestBody SocialRegisterAndLoginDto socialRegisterAndLoginDto
    ) {
        return userService.socialLogin(socialRegisterAndLoginDto);
    }

    @PostMapping("/duplicate/email")
    public ResponseEntity<Map<String, String>> duplicateEmailCheck(
            @RequestBody DuplicateEmailDto duplicateEmailDto
    ) {
        try {
            userService.basicUserEmailDuplicateCheck(duplicateEmailDto.getEmail());
            return ResponseEntity.ok().body(Map.of("message", "사용가능한 이메일입니다."));
        } catch (EmailDuplicateException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));

        }
    }

    @PostMapping("/duplicate/name")
    public ResponseEntity<Map<String, String>> duplicateNameCheck(
            @RequestBody DuplicateNameDto duplicateNameDto
    ) {
        try {
            userService.basicUserNameDuplicateCheck(duplicateNameDto.getName());
            return ResponseEntity.ok().body(Map.of("message", "사용가능한 닉네임입니다."));
        } catch (NameDuplicateException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/user-page")
    public FindUserPageResponseDto findUserPage(
            @RequestBody FindUserPageRequestDto findUserPageRequestDto
    ) {
        return userService.findUserPage(findUserPageRequestDto);
    }
}
