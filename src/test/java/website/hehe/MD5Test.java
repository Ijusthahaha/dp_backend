package website.hehe;

import org.junit.jupiter.api.Test;
import website.hehe.utils.MD5Utils;

public class MD5Test {
    @Test
    public void test() {
        System.out.println(MD5Utils.encode("123456"));
    }
}
