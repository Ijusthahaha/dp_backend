package website.hehe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.pojo.Class;
import website.hehe.pojo.Student;
import website.hehe.pojo.vo.ClassDataDisplay;
import website.hehe.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【class】的数据库操作Service
 * @createDate 2023-12-18 18:55:38
 */
public interface ClassService extends IService<Class> {

    Result<List<Class>> getAllClasses();

    Result<Map<String, Number>> getClassData(Integer classId);

    Result<Object> createClass(ClassDataDisplay classDataDisplay);

    Result<Object> deleteClass(Integer classId);

    void getClassExcel(HttpServletResponse response);

    List<Student> uploadClassExcel(MultipartFile file, String className);
}
