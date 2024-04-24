package website.hehe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.pojo.Student;
import website.hehe.pojo.vo.ModifyStudent;
import website.hehe.pojo.vo.StudentDataDisplay;
import website.hehe.utils.Result;

import java.util.List;
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

    Result<List<StudentDataDisplay>> getAllStudents();

    Result<List<Student>> getStudents(String keyword);

    Result<List<StudentDataDisplay>> getAllClassStudents(String className);

    Result<List<String>> getAllClasses();

    Result<Object> insertStudent(String token, StudentDataDisplay studentDataDisplay);

    void getStudentExcel(String token, HttpServletResponse response);

    List<Student> uploadStudentExcel(MultipartFile file);

    Result<Object> modifyStudent(ModifyStudent modifyStudent);

    Result<List<Map<String, Integer>>> getTopDpStudents();
}
