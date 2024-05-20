package website.hehe.utils;

import lombok.Getter;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenSignKeyUtils {

    @Getter
    private final static String tokenSignKey;

    static {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);

        tokenSignKey = Base64.getEncoder().encodeToString(keyBytes);
    }
}
