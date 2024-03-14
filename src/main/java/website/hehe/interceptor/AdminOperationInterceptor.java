package website.hehe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import website.hehe.utils.JwtUtils;
import website.hehe.utils.Result;

import static website.hehe.utils.AuthUtils.checkAdmin;

@Component
@Setter(onMethod_ = @Autowired)
public class AdminOperationInterceptor implements HandlerInterceptor {

    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        Integer userId = jwtUtils.getUserId(token);
        Result<Object> objectResult = checkAdmin(userId);
        return objectResult.getCode() != 500;
    }
}
