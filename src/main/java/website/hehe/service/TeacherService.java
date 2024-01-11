package website.hehe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import website.hehe.pojo.Teacher;
import website.hehe.utils.Result;

import java.util.Map;

/**
 * @author hehe
 * @description 针对表【teacher】的数据库操作Service
 * @createDate 2023-12-14 15:46:27
 */
public interface TeacherService extends IService<Teacher> {

    Result<Map<String, String>> login(Teacher teacher);

    Result<Object> checkLogin(String token);
}
