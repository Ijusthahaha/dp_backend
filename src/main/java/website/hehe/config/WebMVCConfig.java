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

@Configuration
@Setter(onMethod_ = @Autowired)
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO: add as list
        registry.addInterceptor(tokenLogInterceptor()).addPathPatterns("/log").addPathPatterns("/appeal");
        registry.addInterceptor(superOperationInterceptor()).addPathPatterns("/appeal/getPendingAppeals").addPathPatterns("/appeal/rejectAppeals").addPathPatterns("/appeal/fulfillAppeals");
        registry.addInterceptor(adminOperationInterceptor()).addPathPatterns("/student/insertStudent").addPathPatterns("/student/getStudentExcel").addPathPatterns("/student/uploadStudentExcel").addPathPatterns("/student/modifyStudent");
    }

    //    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true).allowedHeaders(CorsConfiguration.ALL).allowedMethods(CorsConfiguration.ALL).maxAge(3600);
//    }

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
