package website.hehe.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import website.hehe.interceptor.AdminOperationInterceptor;
import website.hehe.interceptor.SuperOperationInterceptor;
import website.hehe.interceptor.TokenLogInterceptor;
import website.hehe.interceptor.utils.InterceptorList;

import java.util.Arrays;
import java.util.List;

@Configuration
@Setter(onMethod_ = @Autowired)
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> tokenLogInterceptor = List.of(InterceptorList.TOKEN_LOG);
        List<String> superOperationInterceptor = List.of(InterceptorList.SUPER_OPERATION);
        List<String> adminOperationInterceptor = List.of(InterceptorList.ADMIN_OPERATION);

        registry.addInterceptor(adminOperationInterceptor()).addPathPatterns(adminOperationInterceptor);
        registry.addInterceptor(superOperationInterceptor()).addPathPatterns(superOperationInterceptor);
        registry.addInterceptor(tokenLogInterceptor()).addPathPatterns(tokenLogInterceptor);
    }

    @Bean(name = "tokenLog")
    public TokenLogInterceptor tokenLogInterceptor() {
        return new TokenLogInterceptor();
    }

    @Bean(name = "adminOperation")
    public AdminOperationInterceptor adminOperationInterceptor() {
        return new AdminOperationInterceptor();
    }

    @Bean(name = "superOperation")
    public SuperOperationInterceptor superOperationInterceptor() {
        return new SuperOperationInterceptor();
    }
}
