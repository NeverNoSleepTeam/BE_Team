package NS.pgmg.service;

import NS.pgmg.domain.user.User;
import NS.pgmg.dto.login.LoginDto;
import NS.pgmg.dto.login.SocialRegisterAndLoginDto;
import NS.pgmg.repository.user.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;

    SecretKey key = Jwts.SIG.HS256.key().build();

    @Transactional(readOnly = true)
    public ResponseEntity<String> login(LoginDto request) {

        String requestEmail = request.getEmail();
        User findUser = userRepository.findByEmail(requestEmail);

        if (!request.getEmail().equals(findUser.getEmail())) {
            return new ResponseEntity<>("이메일이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else if (!request.getPasswd().equals(findUser.getPasswd())) {
            return new ResponseEntity<>("비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            String jws = Jwts.builder().subject(requestEmail).signWith(key).compact();
            return new ResponseEntity<>(jws, HttpStatus.OK);
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
            String jws = Jwts.builder().subject(requestEmail).signWith(key).compact();
            return new ResponseEntity<>(jws, HttpStatus.OK);
        } else if (findUser.isSocialTF()) {
            String jws = Jwts.builder().subject(requestEmail).signWith(key).compact();
            return new ResponseEntity<>(jws, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("일반 유저이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
