package website.hehe.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.hehe.mapper.TeacherMapper;
import website.hehe.pojo.Teacher;

@Component
public class AuthUtils {

    private static AuthUtils authUtils;

    @Autowired
    private TeacherMapper teacherMapper;

    @PostConstruct
    public void init() {
        authUtils = this;
        authUtils.teacherMapper = this.teacherMapper;
    }


    // TODO: maybe interceptor?
    public static Result<Object> checkTeacherLevel(Integer userId) {
        Teacher teacher = authUtils.teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getTeacherId, userId));

        // TODO: test this, dont be embarrassing
        if (teacher == null || teacher.getTeacherLevel() < 2) {
            return Result.fail(null);
        }
        return Result.success(null);
    }

    public static Result<Object> checkAdmin(Integer userId) {
        Teacher teacher = authUtils.teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getTeacherId, userId));
        if (teacher == null || teacher.getTeacherLevel() != 3) {
            return Result.fail(null);
        }
        return Result.success(null);
    }
}
