package NS.pgmg.service;

import NS.pgmg.repository.user.BasicUserRepository;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

@SpringBootTest
class JwtTest {

    @Autowired
    BasicUserRepository basicUserRepository;
    SecretKey key = Jwts.SIG.HS256.key().build();

    @Test
    void jwtTokenTest() {

        String email = "woans5970@naver.com";

        basicUserRepository.findByEmail(email);

        String jws = Jwts.builder().subject(email).signWith(key).compact();

        Assertions.assertThat(Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jws)
                        .getPayload()
                        .getSubject())
                .isEqualTo(email);

        System.out.println(Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jws)
                .getPayload()
                .getSubject());
    }
}