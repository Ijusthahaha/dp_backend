package website.hehe.service;

import website.hehe.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import website.hehe.utils.Result;

import java.util.Map;

/**
* @author hehe
* @description 针对表【student】的数据库操作Service
* @createDate 2023-12-14 14:03:34
*/
public interface StudentService extends IService<Student> {

    Result<Map<String, String>> login(Student student);

    Result<Map<String, String>> checkLogin(String token);

    Result<Object> changePassword(String token, String password);
}
