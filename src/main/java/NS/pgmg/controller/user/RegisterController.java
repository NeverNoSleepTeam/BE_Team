package NS.pgmg.controller.user;

import NS.pgmg.dto.duplicate.DuplicateEmailDto;
import NS.pgmg.dto.duplicate.DuplicateNameDto;
import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.service.user.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Tag(name = "Register", description = "회원가입 관련 API")
@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @Operation(summary = "일반 회원가입")
    @PostMapping("/user/join/basic")
    public ResponseEntity<Map<String, String>> basicUserRegister(
            @RequestBody BasicRegisterDto basicRegisterDto
    ) {
        log.info("Call RegisterController.basicUserRegister");
        return registerService.createBasicUser(basicRegisterDto);
    }

    @Operation(summary = "모델 회원가입")
    @PostMapping("/user/join/model")
    public ResponseEntity<Map<String, String>> ModelUserRegister(
            @RequestBody ModelRegisterDto modelRegisterDto
    ) {
        log.info("Call RegisterController.ModelUserRegister");
        return registerService.createModelUser(modelRegisterDto);
    }

    @Operation(summary = "사진기사 회원가입")
    @PostMapping(value = "/user/join/pro-photo",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> proPhotoRegister(
            @RequestPart(value = "RequestBody") ProPhotoRegisterDto proPhotoRegisterDto,
            @RequestPart(value = "File", required = false) MultipartFile file
    ) {
        log.info("Call RegisterController.proPhotoRegister");
        return registerService.createProPhotoUser(proPhotoRegisterDto, file);
    }

    @Operation(summary = "이메일 중복 확인")
    @PostMapping("/user/duplicate/email")
    public ResponseEntity<Map<String, String>> duplicateEmailCheck(
            @RequestBody DuplicateEmailDto duplicateEmailDto
    ) {
        log.info("Call RegisterController.duplicateEmailCheck");
        try {
            registerService.basicUserEmailDuplicateCheck(duplicateEmailDto.getEmail());
            return ResponseEntity.ok().body(Map.of("message", "사용가능한 이메일입니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));

        }
    }

    @Operation(summary = "닉네임 중복 확인")
    @PostMapping("/user/duplicate/name")
    public ResponseEntity<Map<String, String>> duplicateNameCheck(
            @RequestBody DuplicateNameDto duplicateNameDto
    ) {
        log.info("Call RegisterController.duplicateNameCheck");
        try {
            registerService.basicUserNameDuplicateCheck(duplicateNameDto.getName());
            return ResponseEntity.ok().body(Map.of("message", "사용가능한 닉네임입니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.ok().body(Map.of("message", e.getMessage()));
        }
    }
}
