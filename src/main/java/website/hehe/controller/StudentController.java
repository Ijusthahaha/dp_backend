package website.hehe.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.pojo.Student;
import website.hehe.pojo.vo.ModifyStudent;
import website.hehe.pojo.vo.StudentDataDisplay;
import website.hehe.service.StudentService;
import website.hehe.utils.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class StudentController {
    private StudentService studentService;

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Student student) {
        return studentService.login(student);
    }

    @GetMapping("/checkLogin")
    public Result<Map<String, String>> checkLogin(@RequestHeader String token) {
        return studentService.checkLogin(token);
    }

    @PutMapping("/changePassword")
    public Result<Object> changePassword(@RequestHeader String token, @RequestBody String password) {
        return studentService.changePassword(token, password);
    }

    @PutMapping("/insertStudent")
    public Result<Object> insertStudent(@RequestHeader String token, @RequestBody StudentDataDisplay studentDataDisplay) {
        return studentService.insertStudent(token, studentDataDisplay);
    }

    @GetMapping("/getStudents")
    public Result<List<Student>> getStudents(String keyword) {
        return studentService.getStudents(keyword);
    }

    @GetMapping("/getAllStudents")
    public Result<List<StudentDataDisplay>> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/getAllClassStudents")
    public Result<List<StudentDataDisplay>> getAllClassStudents(String className) {
        return studentService.getAllClassStudents(className);
    }

    @Deprecated
    @GetMapping("/getAllClasses")
    public Result<List<String>> getAllClasses() {
        return studentService.getAllClasses();
    }

    @GetMapping("/getTotalStudent")
    public Result<Integer> getTotalStudent() {
        return Result.success(studentService.list().size());
    }

    @GetMapping("/getStudentExcel")
    public void getStudentExcel(@RequestHeader String token, HttpServletResponse response) {
        studentService.getStudentExcel(token, response);
    }

    @PostMapping("/uploadStudentExcel")
    public Result<Object> uploadStudentExcel(@RequestHeader String token, MultipartFile file) {
        List<Student> students = studentService.uploadStudentExcel(file);
        boolean status = false;
        if (!students.isEmpty()) {
            status = studentService.saveBatch(students);
        }
        return Result.success(status);
    }

    @PutMapping("/modifyStudent")
    public Result<Object> modifyStudent(@RequestHeader String token, @RequestBody ModifyStudent modifyStudent) {
        return studentService.modifyStudent(modifyStudent);
    }

    @GetMapping("/getTopDpStudents")
    public Result<List<Map<String, Integer>>> getTopDpStudents() {
        return studentService.getTopDpStudents();
    }
}
