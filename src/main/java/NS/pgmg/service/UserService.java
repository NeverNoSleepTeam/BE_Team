package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.dto.userpage.*;
import NS.pgmg.exception.EmailDuplicateException;
import NS.pgmg.exception.NameDuplicateException;
import NS.pgmg.exception.PasswordMismatchException;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
                    .socialTF(false)
                    .build();

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "일반 회원가입이 완료됐습니다."));
        } catch (PasswordMismatchException | EmailDuplicateException | NameDuplicateException e) {
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
            savedPath = emptyFileCheck(file, email);
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

        if (!request.getEmail().equals(findUser.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("message", "이메일이 올바르지 않습니다."));
        } else if (!request.getPasswd().equals(findUser.getPasswd())) {
            return ResponseEntity.badRequest().body(Map.of("message", "비밀번호가 올바르지 않습니다."));
        } else {
            return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(requestEmail)));
        }
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
                    .socialTF(true)
                    .build();
            userRepository.save(user);
            return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(requestEmail)));
        } else if (findUser.isSocialTF()) {
            return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(requestEmail)));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
        }
    }

    /**
     * 사용자페이지 조회
     */
    @Transactional
    public FindUserPageResponseDto findUserPage(FindByNameDto request) {

        User findUser = userRepository.findByName(request.getName());

        return FindUserPageResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .userInfo("일반회원")
                .intro(findUser.getIntro())
                .profileImgPath(findUser.getProfileImgPath())
                .build();
    }

    /**
     * 일반 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findBasicInfo(FindByNameDto request) {

        User findUser = userRepository.findByName(request.getName());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 닉네임입니다."));
        }

        FindBasicInfoResponseDto response = FindBasicInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .gender(findUser.getGender())
                .city(findUser.getCity())
                .nationality(findUser.getNationality())
                .intro(findUser.getIntro())
                .profileImgPath(findUser.getProfileImgPath())
                .build();

        return ResponseEntity.ok().body(response);
    }

    /**
     * 모델 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findModelInfo(FindByNameDto request) {

        User findUser = userRepository.findByName(request.getName());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 닉네임입니다."));
        }

        FindModelInfoResponseDto response = FindModelInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .height(findUser.getHeight())
                .weight(findUser.getWeight())
                .top(findUser.getTop())
                .bottom(findUser.getBottom())
                .shoes(findUser.getShoes())
                .build();

        return ResponseEntity.ok().body(response);
    }

    /**
     * 사진기사 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findProPhotoInfo(FindByNameDto request) {

        User findUser = userRepository.findByName(request.getName());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 닉네임입니다."));
        }

        FindProPhotoInfoResponseDto response = FindProPhotoInfoResponseDto.builder()
                .email(findUser.getEmail())
                .name(findUser.getName())
                .businessTrip(findUser.getBusinessTrip())
                .correction(findUser.getCorrection())
                .production(findUser.getProduction())
                .portfolioPath(findUser.getPortfolioPath())
                .build();

        return ResponseEntity.ok().body(response);
    }

    /**
     * 전체 정보 조회
     */
    @Transactional
    public ResponseEntity<?> findAllInfo(FindByNameDto request) {

        User findUser = userRepository.findByName(request.getName());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "존재하지 않는 닉네임입니다."));
        }
        return ResponseEntity.ok().body(findUser);
    }

    /**
     * 마이페이지 수정
     */
    @Transactional
    public ResponseEntity<Map<String, String>> updateUserPage(String token, MultipartFile pdf) {
        return ResponseEntity.ok().body(Map.of("message", "마이페이지 수정이 완료됐습니다."));
    }

    /**
     * 이메일 중복 검사
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserEmailDuplicateCheck(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser != null) {
            throw new EmailDuplicateException("이미 존재하는 이메일입니다.");
        }
    }

    /**
     * 닉네임 중복 검사
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void basicUserNameDuplicateCheck(String name) {
        User findUser = userRepository.findByEmail(name);
        if (findUser != null) {
            throw new NameDuplicateException("이미 존재하는 닉네임입니다.");
        }
    }

    /**
     * 비밀번호 검사 함수
     */
    private void passwdValidation(String passwd, String passwd2) {
        if (!passwd.equals(passwd2)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * PDF 저장 함수
     */
    private String emptyFileCheck(MultipartFile file, String email) {

        if (file == null) {
            return "파일이 없습니다.";
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
}

