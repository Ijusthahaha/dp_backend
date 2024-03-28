package website.hehe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.pojo.Student;
import website.hehe.pojo.Teacher;
import website.hehe.pojo.vo.ModifyTeacher;
import website.hehe.pojo.vo.TeacherDataDisplay;
import website.hehe.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【teacher】的数据库操作Service
 * @createDate 2023-12-14 15:46:27
 */
public interface TeacherService extends IService<Teacher> {

    Result<Map<String, String>> login(Teacher teacher);

    Result<Object> checkLogin(String token);

    Result<List<Teacher>> getAllTeachers();

    Result<Object> modifyTeacher(ModifyTeacher modifyTeacher);

    Result<Object> deleteTeacher(Integer id);

    Result<Object> insertTeacher(String token, TeacherDataDisplay teacherDataDisplay);

    void getTeacherExcel(String token, HttpServletResponse response);

    List<Teacher> uploadTeacherExcel(MultipartFile file);
}
