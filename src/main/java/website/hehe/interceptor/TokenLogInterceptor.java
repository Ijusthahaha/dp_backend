package website.hehe.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import website.hehe.utils.JwtUtils;
import website.hehe.utils.Result;
import website.hehe.utils.ResultEnum;

@Component
@Setter(onMethod_ = @Autowired)
public class TokenLogInterceptor implements HandlerInterceptor {

    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        boolean expiration = jwtUtils.parseToken(token);
        if (!expiration) {
            Result<Object> build = Result.build(ResultEnum.NOTLOGIN, null);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(build);
            response.getWriter().print(s);
            return false;
        }
        return true;
    }
}
