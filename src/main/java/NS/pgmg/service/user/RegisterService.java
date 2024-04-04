package NS.pgmg.service.user;

import NS.pgmg.domain.user.User;
import NS.pgmg.domain.user.enums.UserRank;
import NS.pgmg.dto.register.BasicRegisterDto;
import NS.pgmg.dto.register.ModelRegisterDto;
import NS.pgmg.dto.register.ProPhotoRegisterDto;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static NS.pgmg.service.CommonMethod.updatePDF;


@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

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
            savedPath = updatePDF(file, findUser.getPortfolioPath());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        findUser.setProPhotoInfo(request, savedPath);
        userRepository.save(findUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "기사 회원가입이 완료됐습니다."));
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
     * 비밀번호 검사 함수
     */
    private void passwdValidation(String passwd, String passwd2) {
        if (!passwd.equals(passwd2)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}

