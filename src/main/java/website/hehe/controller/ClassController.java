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
import website.hehe.utils.Operations;
import website.hehe.utils.annotations.AccessLimit;
import website.hehe.utils.annotations.OperateLog;
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

//    @OperateLog(operateModel = "Class", operateType = Operations.Get, operateDesc = "Attempt to get total classes")
    @AccessLimit
    @GetMapping("/getTotalClass")
    public Result<Integer> getTotalClass() {
        return Result.success(classService.list().size());
    }

//    @OperateLog(operateModel = "Class", operateType = Operations.Get, operateDesc = "Attempt to get all classes")
    @AccessLimit
    @GetMapping("/getAllClasses")
    public Result<List<Class>> getAllClasses() {
        return classService.getAllClasses();
    }

//    @OperateLog(operateModel = "Class", operateType = Operations.Get, operateDesc = "Attempt to get class data")
    @AccessLimit
    @GetMapping("/getClassData")
    public Result<Map<String, Number>> getClassData(Integer classId) {
        return classService.getClassData(classId);
    }

    @OperateLog(operateModel = "Class", operateType = Operations.Insert, operateDesc = "Attempt to get create class")
    @AccessLimit
    @PutMapping("/createClass")
    public Result<Object> createClass(@RequestBody ClassDataDisplay classDataDisplay) {
        return classService.createClass(classDataDisplay);
    }

    @OperateLog(operateModel = "Class", operateType = Operations.Delete, operateDesc = "Attempt to get delete class")
    @AccessLimit
    @DeleteMapping("/deleteClass")
    public Result<Object> deleteClass(@RequestHeader Integer classId) {
        return classService.deleteClass(classId);
    }

//    @OperateLog(operateModel = "Class", operateType = Operations.Get, operateDesc = "Attempt to get class excel")
    @AccessLimit(times = 1)
    @GetMapping("/getClassExcel")
    public void getTeacherExcel(HttpServletResponse response) {
        classService.getClassExcel(response);
    }

    @OperateLog(operateModel = "Class", operateType = Operations.Insert, operateDesc = "Attempt to insert class by excel")
    @AccessLimit(times = 1)
    @PostMapping("/uploadClassExcel")
    public Result<Object> uploadTeacherExcel(MultipartFile file, @RequestHeader String className) {
        List<Student> classes = classService.uploadClassExcel(file, className);
        if (!classes.isEmpty()) {
            studentService.saveBatch(classes);
        }
        return Result.success(null);
    }
}
