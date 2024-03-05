package NS.pgmg.service;

import NS.pgmg.domain.user.BasicUser;
import NS.pgmg.dto.LoginDto;
import NS.pgmg.repository.user.BasicUserRepository;
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

    private final BasicUserRepository basicUserRepository;

    SecretKey key = Jwts.SIG.HS256.key().build();

    @Transactional
    public ResponseEntity<String> basicUserLogin(LoginDto request) {

        String requestEmail = request.getEmail();
        BasicUser findBasicUser = basicUserRepository.findByEmail(requestEmail);

        if (!request.getEmail().equals(findBasicUser.getEmail())) {
            return new ResponseEntity<>("이메일이 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else if (!request.getPasswd().equals(findBasicUser.getPasswd())) {
            return new ResponseEntity<>("비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            String jws = Jwts.builder().subject(requestEmail).signWith(key).compact();
            return new ResponseEntity<>(jws, HttpStatus.OK);
        }
    }
}
