package website.hehe.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.hehe.mapper.ClassMapper;
import website.hehe.mapper.StudentMapper;
import website.hehe.pojo.Student;
import website.hehe.service.StudentService;
import website.hehe.utils.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【student】的数据库操作Service实现
 * @createDate 2023-12-14 13:49:21
 */
@Service
@Setter(onMethod_ = @Autowired)
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    private StudentMapper studentMapper;
    private ClassMapper classMapper;
    private JwtUtils jwtUtils;

    @Override
    public Result<Map<String, String>> login(Student student) {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Student::getStudentId, student.getStudentId());
        Student loginUser = studentMapper.selectOne(lambdaQueryWrapper);
        if (loginUser == null) {
            return Result.build(ResultEnum.PASSWORD_ERROR, null);
        }

        try {
            if (!StringUtils.isEmpty(student.getStudentPassword()) &&
                    MD5Utils.encode(student.getStudentPassword()).equals(loginUser.getStudentPassword())) {
                String token = jwtUtils.createToken(student.getStudentId());
                Map<String, String> data = new HashMap<>();
                data.put("token", token);
                return Result.success(data);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return Result.build(ResultEnum.PASSWORD_ERROR, null);
    }

    @Override
    public Result<Map<String, String>> checkLogin(String token) {
        boolean expiration = jwtUtils.parseToken(token);
        if (expiration) {
            Integer userId = jwtUtils.getUserId(token);

            LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Student::getStudentId, userId);
            Student student = studentMapper.selectOne(lambdaQueryWrapper);

            if (student == null || student.getStudentId() == null) {
                return Result.build(ResultEnum.NOTLOGIN, null);
            }

            Map<String, String> map = new HashMap<>();
            map.put("studentUuid", String.valueOf(student.getStudentId()));
            map.put("studentName", student.getStudentName());
            map.put("studentClass", student.getStudentClass());

            Integer classLevelByStudentClass = classMapper.getClassLevelByStudentClass(student.getStudentClass());
            map.put("studentLevel", ClassLevelUtils.parseClassLevel(classLevelByStudentClass).getLevel());

            return Result.success(map);
        }
        return Result.build(ResultEnum.NOTLOGIN, null);
    }

    @Override
    public Result<Object> changePassword(String token, String password) {
        Integer userId = jwtUtils.getUserId(token);

        Student student = new Student();
        try {
            student.setStudentPassword(MD5Utils.encode(password));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        studentMapper.update(student, new UpdateWrapper<Student>().eq("student_id", userId));
        return Result.success(null);
    }
}