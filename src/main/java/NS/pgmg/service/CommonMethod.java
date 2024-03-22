package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;

import java.util.UUID;

public class CommonMethod {

    protected static String tokenCheck(String token) {

        if (token == null) {
            throw new RuntimeException("토큰이 없습니다.");
        }

        return JwtConfig.getEmail(token);
    }

    protected static void emailCheck(String tokenEmail, String requestEmail) {

        if (!tokenEmail.equals(requestEmail)) {
            throw new RuntimeException("토큰 정보와 요청하신 이메일의 정보가 일치하지 않습니다.");
        }

    }

    protected static String createRandomUuid() {
        return UUID.randomUUID().toString();
    }


}
