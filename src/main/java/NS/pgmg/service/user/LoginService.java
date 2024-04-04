package NS.pgmg.service.user;

import NS.pgmg.config.JwtConfig;
import NS.pgmg.domain.user.User;
import NS.pgmg.domain.user.enums.UserRank;
import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, String>> login(LoginDto request) {

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "회원 정보가 없습니다."));
        } else {
            if (findUser.isSocial()) {
                return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
            } else if (!request.getPasswd().equals(findUser.getPasswd())) {
                return ResponseEntity.badRequest().body(Map.of("message", "비밀번호가 올바르지 않습니다."));
            }
        }
        return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(request.getEmail())));
    }

    /**
     * 소셜 로그인
     */
    @Transactional
    public ResponseEntity<Map<String, String>> socialLogin(SocialRegisterAndLoginDto request) {

        User findUser = userRepository.findByEmail(request.getEmail());

        if (findUser == null) {
            User user = User.builder()
                    .email(request.getEmail())
                    .name(UUID.randomUUID().toString().replace("-", "").substring(0, 10))
                    .isSocial(true)
                    .userRank(UserRank.일반회원)
                    .build();
            userRepository.save(user);
            return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(request.getEmail())));
        } else if (findUser.isSocial()) {
            return ResponseEntity.ok().body(Map.of("token", JwtConfig.getToken(request.getEmail())));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "잘못된 접근입니다."));
        }
    }
}

