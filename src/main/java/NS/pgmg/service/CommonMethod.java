package NS.pgmg.service;

import NS.pgmg.config.JwtConfig;

import java.util.UUID;

public class CommonMethod {

    protected static String tokenCheck(String token) {

        if (token == null) {
            return "token is null";
        }

        return JwtConfig.getEmail(token);
    }

    protected static boolean emailCheck(String tokenEmail, String requestEmail) {
        return tokenEmail.equals(requestEmail);
    }

    protected static String createRandomUuid() {
        return UUID.randomUUID().toString();
    }


}
