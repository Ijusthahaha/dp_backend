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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Setter(onMethod_ = @Autowired)
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> tokenLogInterceptor = Arrays
                .asList("/log",
                        "/appeal");
        List<String> superOperationInterceptor = Arrays
                .asList("/appeal/getPendingAppeals",
                        "/appeal/rejectAppeals",
                        "/appeal/fulfillAppeals");
        List<String> adminOperationInterceptor = Arrays
                .asList("/student/insertStudent",
                        "/student/getStudentExcel",
                        "/student/uploadStudentExcel",
                        "/student/modifyStudent",
                        "/getAllStudents",
                        "/getAllTeachers");

        registry.addInterceptor(tokenLogInterceptor()).addPathPatterns(tokenLogInterceptor);
        registry.addInterceptor(superOperationInterceptor()).addPathPatterns(superOperationInterceptor);
        registry.addInterceptor(adminOperationInterceptor()).addPathPatterns(adminOperationInterceptor);
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
