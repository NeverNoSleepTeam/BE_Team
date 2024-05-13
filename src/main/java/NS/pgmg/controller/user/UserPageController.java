package NS.pgmg.controller.user;

import NS.pgmg.dto.userpage.FindByEmailDto;
import NS.pgmg.dto.userpage.UpdateBasicInfoRequestDto;
import NS.pgmg.dto.userpage.UpdateModelInfoRequestDto;
import NS.pgmg.dto.userpage.UpdateProPhotoInfoRequestDto;
import NS.pgmg.service.user.UserPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "User Page", description = "유저페이지(마이페이지) 관련 API")
@RestController
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;

    @Operation(summary = "일반 정보 조회")
    @PostMapping("/user/info/basic")
    public ResponseEntity<?> findBasicInfo(
            @RequestHeader(value = "Token", required = false) String token,
            @RequestBody FindByEmailDto findByEmailDto
    ) {
        log.info("Call UserPageController.findBasicInfo");
        return userPageService.findBasicInfo(token, findByEmailDto);
    }

    @Operation(summary = "모델 정보 조회")
    @PostMapping("/user/info/model")
    public ResponseEntity<?> findModelInfo(
            @RequestHeader(value = "Token", required = false) String token,
            @RequestBody FindByEmailDto findByEmailDto
    ) {
        log.info("Call UserPageController.findModelInfo");
        return userPageService.findModelInfo(token, findByEmailDto);
    }

    @Operation(summary = "사진기사 정보 조회")
    @PostMapping("/user/info/pro-photo")
    public ResponseEntity<?> findProPhotoInfo(
            @RequestHeader(value = "Token", required = false) String token,
            @RequestBody FindByEmailDto findByEmailDto
    ) {
        log.info("Call UserPageController.findProPhotoInfo");
        return userPageService.findProPhotoInfo(token, findByEmailDto);
    }

    @Operation(summary = "전체 정보 조회")
    @PostMapping("/user/info/all")
    public ResponseEntity<?> findAllInfo(
            @RequestHeader(value = "Token", required = false) String token,
            @RequestBody FindByEmailDto findByEmailDto
    ) {
        log.info("Call UserPageController.findAllInfo");
        return userPageService.findAllInfo(token, findByEmailDto);
    }

    @Operation(summary = "일반 정보 수정")
    @PutMapping(value = "/user/info/basic",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> updateBasicInfo(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") UpdateBasicInfoRequestDto updateBasicInfoRequestDto,
            @RequestPart(value = "File", required = false) MultipartFile file
    ) {
        log.info("Call UserPageController.updateBasicInfo");
        return userPageService.updateBasicInfo(token, file, updateBasicInfoRequestDto);
    }

    @Operation(summary = "모델 정보 수정")
    @PutMapping(value = "/user/info/model",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> updateModelInfo(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") UpdateModelInfoRequestDto updateModelInfoRequestDto,
            @RequestPart(value = "File", required = false) MultipartFile file
    ) {
        log.info("Call UserPageController.updateModelInfo");
        return userPageService.updateModelInfo(token, file, updateModelInfoRequestDto);
    }

    @Operation(summary = "사진기사 정보 수정")
    @PutMapping(value = "/user/info/pro-photo",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> updateProPhotoInfo(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") UpdateProPhotoInfoRequestDto updateProPhotoInfoRequestDto,
            @RequestPart(value = "ImgFile", required = false) MultipartFile img,
            @RequestPart(value = "PdfFile", required = false) MultipartFile pdf
    ) {
        log.info("Call UserPageController.updateProPhotoInfo");
        return userPageService.updateProPhotoInfo(token, updateProPhotoInfoRequestDto, img, pdf);
    }

    @Operation(summary = "기본 프로필 사진 수정")
    @PutMapping(value = "/user/info/img/basic",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> transformBasicImg(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") FindByEmailDto findByEmailDto,
            @RequestPart(value = "Files", required = false) List<MultipartFile> files
    ) {
        log.info("Call UserPageController.transformBasicImg");
        return userPageService.changeBasicImg(token, findByEmailDto, files);
    }

    @Operation(summary = "모델 프로필 사진 수정")
    @PutMapping(value = "/user/info/img/model",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> transformModelImg(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") FindByEmailDto findByEmailDto,
            @RequestPart(value = "Files", required = false) List<MultipartFile> files
    ) {
        log.info("Call UserPageController.transformModelImg");
        return userPageService.changeModelImg(token, findByEmailDto, files);
    }

    @Operation(summary = "사진기사 프로필 사진 수정")
    @PutMapping(value = "/user/info/img/pro-photo",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, String>> transformProPhotoImg(
            @RequestHeader(value = "Token") String token,
            @RequestPart(value = "RequestBody") FindByEmailDto findByEmailDto,
            @RequestPart(value = "Files", required = false) List<MultipartFile> files
    ) {
        log.info("Call UserPageController.transformProPhotoImg");
        return userPageService.changeProPhotoImg(token, findByEmailDto, files);
    }
}
