package website.hehe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import website.hehe.utils.JwtUtils;

@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testJwt() {
        //生成 传入用户标识
        String token = jwtUtils.createToken(114514, "student");
        System.out.println("token = " + token);

        //解析用户标识
        int userId = jwtUtils.getUserId(token);
        System.out.println("userId = " + userId);

        //校验是否到期! false 未到期 true到期
        boolean expiration = jwtUtils.parseToken(token);
        System.out.println("non-expiration = " + expiration);
    }
}
