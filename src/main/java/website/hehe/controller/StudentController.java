package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Student;
import website.hehe.service.StudentService;
import website.hehe.utils.Result;

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
}
