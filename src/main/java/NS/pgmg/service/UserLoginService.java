package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<String> login(LoginDto request) {

        String requestEmail = request.getEmail();
        User findUser = userRepository.findByEmail(requestEmail);

        if (!request.getEmail().equals(findUser.getEmail())) {
            return new ResponseEntity<>("이메일이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else if (!request.getPasswd().equals(findUser.getPasswd())) {
            return new ResponseEntity<>("비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            log.info("로그인 이메일 = {}", requestEmail);
            return new ResponseEntity<>(JwtConfig.getToken(requestEmail), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<String> socialLogin(SocialRegisterAndLoginDto request) {

        String requestEmail = request.getEmail();
        User findUser = userRepository.findByEmail(requestEmail);

        if (findUser == null) {
            User user = User.builder()
                    .email(requestEmail)
                    .socialTF(true)
                    .build();
            userRepository.save(user);
            return new ResponseEntity<>(JwtConfig.getToken(requestEmail), HttpStatus.OK);
        } else if (findUser.isSocialTF()) {
            return new ResponseEntity<>(JwtConfig.getToken(requestEmail), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("일반 유저이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
