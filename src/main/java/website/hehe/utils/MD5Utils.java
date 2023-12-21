package website.hehe.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MD5Utils {
    public static String encode(String string) throws UnsupportedEncodingException {
        return DigestUtils.md5DigestAsHex(string.getBytes(StandardCharsets.UTF_8));
    }
}
