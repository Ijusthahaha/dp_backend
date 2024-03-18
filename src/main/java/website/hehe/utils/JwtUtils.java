package website.hehe.utils;

import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtUtils {

    private long tokenExpiration; //有效时间,单位毫秒 1000毫秒 == 1秒
    private String tokenSignKey;  //当前程序签名秘钥

    public String createToken(int id, String user) {
        return Jwts
                .builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .claim("id", id)
                .claim("aud", user)
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000 * 60))
                .signWith(Keys.hmacShaKeyFor(tokenSignKey.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    public boolean parseToken(String token) {
        try {
            Date expiration = Jwts
                    .parser()
                    .verifyWith(Keys.hmacShaKeyFor(tokenSignKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload().getExpiration();
            return !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Integer getUserId(String token) {
        if (StringUtils.isEmpty(token)) return null;

        Jws<Claims> claimsJws = Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(tokenSignKey.getBytes()))
                .build()
                .parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();
        return (Integer) claims.get("id");
    }
}
