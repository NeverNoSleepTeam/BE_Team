package NS.pgmg.config;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class JwtConfig {

    private final SecretKey key;

    private JwtConfig() {
        this.key = Jwts.SIG.HS256.key().build();
    }

    private static class SingletonHelper{
        private static final JwtConfig helper = new JwtConfig();
    }

    private static SecretKey getKey(){
        return SingletonHelper.helper.key;
    }

    public static String getToken(String email) {
        return Jwts.builder().subject(email).signWith(getKey()).compact();
    }

    public static String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
