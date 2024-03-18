package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Teacher;
import website.hehe.service.TeacherService;
import website.hehe.utils.Result;

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

    @GetMapping("getAllTeachers")
    public Result<List<Teacher>> getAllTeachers(@RequestHeader String token) {
        return teacherService.getAllTeachers();
    }
}
