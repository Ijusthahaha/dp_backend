package website.hehe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import website.hehe.interceptor.utils.InterceptText;
import website.hehe.utils.IpUtils;
import website.hehe.utils.annotations.AccessLimit;
import website.hehe.utils.result.Result;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AccessLimitInterceptor.class);
 
    @Autowired
    private StringRedisTemplate redisTemplate;
 
    /**
     * 接口调用前检查对方ip是否频繁调用接口
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // handler是否为 HandleMethod 实例
            if (handler instanceof HandlerMethod handlerMethod) {

                // 获取方法
                Method method = handlerMethod.getMethod();

                // 判断方式是否有AccessLimit注解，有的才需要做限流
                if (!method.isAnnotationPresent(AccessLimit.class)) {
                    return true;
                }
 
                // 获取注解上的内容
                AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
                if (accessLimit == null) {
                    return true;
                }
                // 获取方法注解上的请求次数
                int times = accessLimit.times();

                // 获取方法注解上的请求时间
                int second = accessLimit.second();
 
                // 拼接redis key = IP + Api限流
                String key = "access:" + IpUtils.getIpAddr(request) + request.getRequestURI();

                // 获取redis的value
                Integer maxTimes = null;
                String value = redisTemplate.opsForValue().get(key);
                if (StringUtils.isNotEmpty(value)) {
                    maxTimes = Integer.valueOf(value);
                }
                if (maxTimes == null) {
                    // 如果redis中没有该ip对应的时间则表示第一次调用，保存key到redis
                    redisTemplate.opsForValue().set(key, "1", second, TimeUnit.SECONDS);
                } else if (maxTimes < times) {
                    // 如果redis中的时间比注解上的时间小则表示可以允许访问,这是修改redis的value时间
                    redisTemplate.opsForValue().set(key, maxTimes + 1 + "", second, TimeUnit.SECONDS);
                } else {
                    response.setHeader("Content-Type", "text/html;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print(Result.build(403, "Access Reached Limit!"));
                    response.setStatus(403);
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("Access Limit Interceptor error");
        }
        return true;
    }
}