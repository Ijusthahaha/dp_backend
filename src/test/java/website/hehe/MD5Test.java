package website.hehe;

import org.junit.jupiter.api.Test;
import website.hehe.utils.MD5Utils;

import java.io.UnsupportedEncodingException;

public class MD5Test {
    @Test
    public void test() {
        try {
            System.out.println(MD5Utils.encode("123456"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
