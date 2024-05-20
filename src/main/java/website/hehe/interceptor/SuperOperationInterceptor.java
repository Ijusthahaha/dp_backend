package website.hehe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import website.hehe.utils.JwtUtils;
import website.hehe.utils.result.Result;

import static website.hehe.utils.AuthUtils.checkTeacherLevel;

@Component
@Setter(onMethod_ = @Autowired)
public class SuperOperationInterceptor implements HandlerInterceptor {

    private JwtUtils jwtUtils;

    // verify teacher level. (must be director, teacher_level >= 2)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        Integer userId = jwtUtils.getUserId(token);
        Result<Object> objectResult = checkTeacherLevel(userId);
        return objectResult.getCode() != 500;
    }
}
