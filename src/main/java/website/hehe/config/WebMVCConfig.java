package website.hehe.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import website.hehe.interceptor.TokenLogInterceptor;

@Configuration
@Setter(onMethod_ = @Autowired)
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenLogInterceptor()).addPathPatterns("/log").addPathPatterns("/appeal");
    }
}
