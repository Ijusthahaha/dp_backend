package website.hehe.interceptor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import website.hehe.utils.TokenSignKeyUtils;

@Component
@Setter(onMethod_ = @Autowired)
public class ExpiredJwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (token == null) return true;
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(TokenSignKeyUtils.getTokenSignKey().getBytes())).build().parseSignedClaims(token);
        } catch (Exception ignored) {
            response.setStatus(401);
            return false;
        } return true;
    }
}
