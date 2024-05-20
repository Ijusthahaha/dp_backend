package website.hehe.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.pojo.Teacher;
import website.hehe.pojo.vo.ModifyTeacher;
import website.hehe.pojo.vo.TeacherDataDisplay;
import website.hehe.service.TeacherService;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class TeacherController {
    private TeacherService teacherService;

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Teacher teacher) {
        return teacherService.login(teacher);
    }

    @GetMapping("/checkLogin")
    public Result<Object> checkLogin(@RequestHeader String token) {
        return teacherService.checkLogin(token);
    }

    @GetMapping("/getTotalTeacher")
    public Result<Integer> getTotalTeacher() {
        return Result.success(teacherService.list().size());
    }

    @GetMapping("/getTeacherByUuid")
    public Result<Teacher> getTeacherByUuid(String uuid) {
        return Result.success(teacherService.getById(uuid));
    }

    @GetMapping("/getAllTeachers")
    public Result<List<Teacher>> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PutMapping("/modifyTeacher")
    public Result<Object> modifyTeacher(@RequestBody ModifyTeacher modifyTeacher) {
        return teacherService.modifyTeacher(modifyTeacher);
    }

    @DeleteMapping("/deleteTeacher")
    public Result<Object> deleteTeacher(@RequestHeader Integer id) {
        return teacherService.deleteTeacher(id);
    }

    @PutMapping("/insertTeacher")
    public Result<Object> insertTeacher(@RequestHeader String token, @RequestBody TeacherDataDisplay teacherDataDisplay) {
        return teacherService.insertTeacher(token, teacherDataDisplay);
    }

    @GetMapping("/getTeacherExcel")
    public void getTeacherExcel(@RequestHeader String token, HttpServletResponse response) {
        teacherService.getTeacherExcel(token, response);
    }

    @PostMapping("/uploadTeacherExcel")
    public Result<Object> uploadTeacherExcel(MultipartFile file) {
        List<Teacher> teachers = teacherService.uploadTeacherExcel(file);
        if (!teachers.isEmpty()) {
            teacherService.saveBatch(teachers);
        }
        return Result.success(null);
    }

    @GetMapping("/getTopDpTeachers")
    public Result<List<Map<String, Integer>>> getTopDpTeachers() {
        return teacherService.getTopDpTeachers();
    }
}
