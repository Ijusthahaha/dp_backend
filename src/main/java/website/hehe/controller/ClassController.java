package website.hehe.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.pojo.Class;
import website.hehe.pojo.Student;
import website.hehe.pojo.vo.ClassDataDisplay;
import website.hehe.service.ClassService;
import website.hehe.service.StudentService;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/class")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class ClassController {

    private ClassService classService;
    private StudentService studentService;

    @GetMapping("/getTotalClass")
    public Result<Integer> getTotalClass() {
        return Result.success(classService.list().size());
    }

    @GetMapping("/getAllClasses")
    public Result<List<Class>> getAllClasses() {
        return classService.getAllClasses();
    }

    @GetMapping("/getClassData")
    public Result<Map<String, Number>> getClassData(Integer classId) {
        return classService.getClassData(classId);
    }

    @PutMapping("/createClass")
    public Result<Object> createClass(@RequestBody ClassDataDisplay classDataDisplay) {
        return classService.createClass(classDataDisplay);
    }

    @DeleteMapping("/deleteClass")
    public Result<Object> deleteClass(@RequestHeader Integer classId) {
        return classService.deleteClass(classId);
    }

    @GetMapping("/getClassExcel")
    public void getTeacherExcel(HttpServletResponse response) {
        classService.getClassExcel(response);
    }

    @PostMapping("/uploadClassExcel")
    public Result<Object> uploadTeacherExcel(MultipartFile file, @RequestHeader String className) {
        List<Student> classes = classService.uploadClassExcel(file, className);
        if (!classes.isEmpty()) {
            studentService.saveBatch(classes);
        }
        return Result.success(null);
    }
}
