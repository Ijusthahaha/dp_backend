package website.hehe.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import website.hehe.pojo.Student;
import website.hehe.pojo.Teacher;
import website.hehe.service.TeacherService;
import website.hehe.mapper.TeacherMapper;
import org.springframework.stereotype.Service;
import website.hehe.utils.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
* @author hehe
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2023-12-14 15:46:27
*/
@Service
@Setter(onMethod_ = @Autowired)
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    private TeacherMapper teacherMapper;
    private JwtUtils jwtUtils;

    @Override
    public Result<Map<String, String>> login(Teacher teacher) {
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Teacher::getTeacherId, teacher.getTeacherId());
        Teacher loginUser = teacherMapper.selectOne(lambdaQueryWrapper);
        if (loginUser == null) {
            return Result.build(ResultEnum.PASSWORD_ERROR, null);
        }

        try {
            if (!StringUtils.isEmpty(teacher.getTeacherPassword()) &&
                    MD5Utils.encode(teacher.getTeacherPassword()).equals(loginUser.getTeacherPassword())) {
                String token = jwtUtils.createToken(teacher.getTeacherId());
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
    public Result<Object> checkLogin(String token) {
        boolean expiration = jwtUtils.parseToken(token);
        if (expiration) {
            Integer userId = jwtUtils.getUserId(token);

            LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Teacher::getTeacherId, userId);
            Teacher teacher = teacherMapper.selectOne(lambdaQueryWrapper);

            if (teacher == null || teacher.getTeacherId() == null) {
                return Result.build(ResultEnum.NOTLOGIN, null);
            }

            Map<String, String> map = new HashMap<>();
            map.put("teacherUuid", String.valueOf(teacher.getTeacherId()));
            map.put("teacherName", teacher.getTeacherName());
            map.put("teacherClass", teacher.getTeacherClass());
            map.put("teacherLevel", String.valueOf(teacher.getTeacherLevel()));

            return Result.success(map);
        }
        return Result.build(ResultEnum.NOTLOGIN, null);
    }
}




