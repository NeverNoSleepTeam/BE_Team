package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;
import NS.pgmg.domain.user.User;
import NS.pgmg.domain.user.enums.UserRank;
import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.dto.userpage.*;
import NS.pgmg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static NS.pgmg.service.CommonMethod.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Value("${pdf.path}")
    private String filePath;

    /**
     * 일반 회원가입
     */
    @Transactional
    public ResponseEntity<Map<String,String>> createBasicUser(BasicRegisterDto request) {
        try {
            passwdValidation(request.getPasswd(), request.getPasswd2());
            basicUserEmailDuplicateCheck(request.getEmail());
            basicUserNameDuplicateCheck(request.getName());

            User user = User.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .passwd(request.getPasswd())
                    .gender(request.getGender())
                    .city(request.getCity())
                    .nationality(request.getNationality())
                    .intro(request.getIntro())
                    .userRank(UserRank.일반회원)
                    .isSocial(false)
                    .build();

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "일반 회원가입이 완료됐습니다."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 모델 회원가입
     */
    @Transactional
    public ResponseEntity<Map<String, String>> createModelUser(ModelRegisterDto request) {
        String email = request.getEmail();
        User findUser = userRepository.findByEmail(email);
        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
        }
        findUser.setModelInfo(request);
        userRepository.save(findUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "모델 회원가입이 완료됐습니다."));
    }

    /**
     * 사진기사 회원가입
     */
    @Transactional
    public ResponseEntity<Map<String, String>> createProPhotoUser(ProPhotoRegisterDto request, MultipartFile file) {
        String email = request.getEmail();
        User findUser = userRepository.findByEmail(email);
        if (findUser == null){
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
        }
        String savedPath;
        try {
            savedPath = savePDF(file, email);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        findUser.setProPhotoInfo(request, savedPath);
        userRepository.save(findUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "기사 회원가입이 완료됐습니다."));
    }

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, String>> login(LoginDto request) {

        String requestEmail = request.getEmail();
        User findUser = userRepository.findByEmail(requestEmail);

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "회원 정보가 없습니다."));
        } else {
            if (findUser.isSocial()) {
                return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
            } else if (!request.getPasswd().equals(findUser.getPasswd())) {
                return ResponseEntity.badRequest().body(Map.of("message", "비밀번호가 올바르지 않습니다."));
            }
        }
        return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(requestEmail)));
    }

    /**
     * 소셜 로그인
     */
    @Transactional
    public ResponseEntity<Map<String, String>> socialLogin(SocialRegisterAndLoginDto request) {

        String requestEmail = request.getEmail();
        User findUser = userRepository.findByEmail(requestEmail);

        if (findUser == null) {
            User user = User.builder()
                    .email(requestEmail)
                    .name(UUID.randomUUID().toString().replace("-", "").substring(0, 10))
                    .isSocial(true)
                    .build();
            userRepository.save(user);
            return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(requestEmail)));
        } else if (findUser.isSocial()) {
            return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(requestEmail)));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
        }
    }

    /**
     * 일반 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findBasicInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        return ResponseEntity.ok().body(FindBasicInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .gender(findUser.getGender())
                .city(findUser.getCity())
                .nationality(findUser.getNationality())
                .intro(findUser.getIntro())
                .userRank(findUser.getUserRank())
                .profileBasicImgPath(findUser.getProfileBasicImgPath())
                .isSelf(isSelf)
                .build());
    }

    /**
     * 모델 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findModelInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        return ResponseEntity.ok().body(FindModelInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .gender(findUser.getGender())
                .city(findUser.getCity())
                .nationality(findUser.getNationality())
                .intro(findUser.getIntro())
                .userRank(findUser.getUserRank())
                .profileModelImgPath(findUser.getProfileModelImgPath())
                .height(findUser.getHeight())
                .weight(findUser.getWeight())
                .top(findUser.getTop())
                .bottom(findUser.getBottom())
                .shoes(findUser.getShoes())
                .isSelf(isSelf)
                .build());
    }

    /**
     * 사진기사 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findProPhotoInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        return ResponseEntity.ok().body(FindProPhotoInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .gender(findUser.getGender())
                .city(findUser.getCity())
                .nationality(findUser.getNationality())
                .intro(findUser.getIntro())
                .userRank(findUser.getUserRank())
                .businessTrip(findUser.getBusinessTrip())
                .correction(findUser.getCorrection())
                .production(findUser.getProduction())
                .profileProPhotoImgPath(findUser.getProfileProPhotoImgPath())
                .portfolioPath(findUser.getPortfolioPath())
                .isSelf(isSelf)
                .build());
    }

    /**
     * 전체 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findAllInfo(String token, FindByEmailDto request) {

        boolean isSelf = (token != null) && tokenCheck(token).equals(request.getEmail());

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }
        return ResponseEntity.ok().body(Map.of("requestBody", findUser, "self", isSelf));
    }

    /**
     * 일반 정보 수정
     */
    @Transactional
    public ResponseEntity<Map<String, String>> updateBasicInfo(String token, UpdateBasicInfoRequestDto request) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            User findUser = userRepository.findByEmail(request.getEmail());
            findUser.updateBasicInfo(request);
            userRepository.save(findUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("message", "수정이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateModelInfo(String token, UpdateModelInfoRequestDto request) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            User findUser = userRepository.findByEmail(request.getEmail());
            findUser.updateModelInfo(request);
            userRepository.save(findUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("message", "수정이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateProPhotoInfo(String token, UpdateProPhotoInfoRequestDto request,
                                                                  MultipartFile file) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
            User findUser = userRepository.findByEmail(request.getEmail());
            findUser.updateProPhotoInfo(request, updatePDF(file, findUser.getPortfolioPath()));
            userRepository.save(findUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok().body(Map.of("message", "수정이 완료됐습니다."));
    }

    /**
     * 이메일 중복 검사
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserEmailDuplicateCheck(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
    }

    /**
     * 닉네임 중복 검사
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserNameDuplicateCheck(String name) {
        User findUser = userRepository.findByName(name);
        if (findUser != null) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }
    }

    /**
     * 회원 등급 변경(일반)
     */
    @Transactional
    public ResponseEntity<Map<String, String>> changeBasicRank(String token, FindByEmailDto request) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        findUser.updateRank(UserRank.일반회원);
        userRepository.save(findUser);
        return ResponseEntity.ok().body(Map.of("message", "프로필 전환이 완료됐습니다."));
    }

    /**
     * 회원 등급 변경(모델)
     */
    @Transactional
    public ResponseEntity<Map<String, String>> changeModelRank(String token, FindByEmailDto request) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        findUser.updateRank(UserRank.모델회원);
        userRepository.save(findUser);
        return ResponseEntity.ok().body(Map.of("message", "프로필 전환이 완료됐습니다."));
    }

    /**
     * 회원 등급 변경(사진기사)
     */
    @Transactional
    public ResponseEntity<Map<String, String>> changeProPhotoRank(String token, FindByEmailDto request) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 이메일입니다."));
        }

        findUser.updateRank(UserRank.사진기사회원);
        userRepository.save(findUser);
        return ResponseEntity.ok().body(Map.of("message", "프로필 전환이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String,String>> changeBasicImg(String token, FindByEmailDto request,
                                                             List<MultipartFile> files) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());
        findUser.changeBasicImg(updateFiles(files, findUser.getProfileBasicImgPath(), filePath));
        userRepository.save(findUser);

        return ResponseEntity.ok().body(Map.of("message", "변경이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> changeModelImg(String token, FindByEmailDto request,
                                                              List<MultipartFile> files) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());
        findUser.changeModelImg(updateFiles(files, findUser.getProfileModelImgPath(), filePath));
        userRepository.save(findUser);

        return ResponseEntity.ok().body(Map.of("message", "변경이 완료됐습니다."));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> changeProPhotoImg(String token, FindByEmailDto request,
                                                                 List<MultipartFile> files) {
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByEmail(request.getEmail());
        findUser.changeProPhotoImg(updateFiles(files, findUser.getProfileProPhotoImgPath(), filePath));
        userRepository.save(findUser);

        return ResponseEntity.ok().body(Map.of("message", "변경이 완료됐습니다."));
    }

    /**
     * 비밀번호 검사 함수
     */
    private void passwdValidation(String passwd, String passwd2) {
        if (!passwd.equals(passwd2)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * PDF 저장 함수
     */
    private String savePDF(MultipartFile file, String email) {

        if (file == null) {
            return null;
        }

        String fullPath = filePath + email + ".pdf";
        String savedPath = "/portfolio/" + email + ".pdf";

        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw new RuntimeException("PDF 저장 오류");
        }

        return savedPath;
    }

    private String updatePDF(MultipartFile file, String path) {

        String fileName;

        if (path != null) {
            fileName = path.replace("/portfolio/", "");
            deleteFile(fileName, filePath);
        }

        if (file != null) {
            fileName = createRandomUuid() + ".pdf";

            try {
                file.transferTo(new File(filePath + fileName));
            } catch (IOException e) {
                throw new RuntimeException("pdf 저장 오류", e);
            }

            return "/portfolio/" + fileName;
        }

        return null;
    }
}

