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
import website.hehe.utils.Operations;
import website.hehe.utils.annotations.AccessLimit;
import website.hehe.utils.annotations.OperateLog;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class TeacherController {
    private TeacherService teacherService;

    @OperateLog(operateModel = "Teacher", operateType = Operations.Login, operateDesc = "Attempt to use ID and password to login")
    @AccessLimit
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Teacher teacher) {
        return teacherService.login(teacher);
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Login, operateDesc = "Attempt to use the previous token to login")
    @AccessLimit
    @GetMapping("/checkLogin")
    public Result<Object> checkLogin(@RequestHeader String token) {
        return teacherService.checkLogin(token);
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Get, operateDesc = "Attempt to get total teacher count")
    @AccessLimit
    @GetMapping("/getTotalTeacher")
    public Result<Integer> getTotalTeacher() {
        return Result.success(teacherService.list().size());
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Get, operateDesc = "Attempt to get teacher by UUID")
    @AccessLimit
    @GetMapping("/getTeacherByUuid")
    public Result<Teacher> getTeacherByUuid(String uuid) {
        return Result.success(teacherService.getById(uuid));
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Get, operateDesc = "Attempt to get all teachers")
    @AccessLimit
    @GetMapping("/getAllTeachers")
    public Result<List<Teacher>> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Modify, operateDesc = "Attempt to modify teacher")
    @AccessLimit
    @PutMapping("/modifyTeacher")
    public Result<Object> modifyTeacher(@RequestBody ModifyTeacher modifyTeacher) {
        return teacherService.modifyTeacher(modifyTeacher);
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Delete, operateDesc = "Attempt to delete teacher")
    @AccessLimit
    @DeleteMapping("/deleteTeacher")
    public Result<Object> deleteTeacher(@RequestHeader Integer id) {
        return teacherService.deleteTeacher(id);
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Insert, operateDesc = "Attempt to insert teacher")
    @AccessLimit
    @PutMapping("/insertTeacher")
    public Result<Object> insertTeacher(@RequestHeader String token, @RequestBody TeacherDataDisplay teacherDataDisplay) {
        return teacherService.insertTeacher(token, teacherDataDisplay);
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Get, operateDesc = "Attempt to get teacher excel")
    @AccessLimit(times = 1)
    @GetMapping("/getTeacherExcel")
    public void getTeacherExcel(@RequestHeader String token, HttpServletResponse response) {
        teacherService.getTeacherExcel(token, response);
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Insert, operateDesc = "Attempt to insert teachers by excel")
    @AccessLimit(times = 1)
    @PostMapping("/uploadTeacherExcel")
    public Result<Object> uploadTeacherExcel(MultipartFile file) {
        List<Teacher> teachers = teacherService.uploadTeacherExcel(file);
        if (!teachers.isEmpty()) {
            teacherService.saveBatch(teachers);
        }
        return Result.success(null);
    }

    @OperateLog(operateModel = "Teacher", operateType = Operations.Get, operateDesc = "Attempt to get top DP teachers")
    @AccessLimit
    @GetMapping("/getTopDpTeachers")
    public Result<List<Map<String, Integer>>> getTopDpTeachers() {
        return teacherService.getTopDpTeachers();
    }
}
