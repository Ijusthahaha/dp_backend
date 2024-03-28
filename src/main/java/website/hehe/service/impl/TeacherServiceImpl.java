package website.hehe.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.mapper.TeacherMapper;
import website.hehe.pojo.Student;
import website.hehe.pojo.Teacher;
import website.hehe.pojo.vo.ModifyTeacher;
import website.hehe.pojo.vo.TeacherDataDisplay;
import website.hehe.service.TeacherService;
import website.hehe.utils.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author hehe
 * @description 针对表【teacher】的数据库操作Service实现
 * @createDate 2023-12-14 15:46:27
 */
@Service
@Setter(onMethod_ = @Autowired)
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {

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

        if (!StringUtils.isEmpty(teacher.getTeacherPassword()) &&
                MD5Utils.encode(teacher.getTeacherPassword()).equals(loginUser.getTeacherPassword())) {
            String token = jwtUtils.createToken(teacher.getTeacherId(), "teacher");
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            return Result.success(data);
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

    @Override
    public Result<List<Teacher>> getAllTeachers() {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.select(Teacher.class, info -> !info.getColumn().equals("teacher_password")).orderByAsc("teacher_name", "teacher_level");
        List<Teacher> teachers = teacherMapper.selectList(teacherQueryWrapper);
        return Result.success(teachers);
    }

    @Override
    public Result<Object> modifyTeacher(ModifyTeacher modifyTeacher) {
        Teacher teacher = new Teacher();
        teacher.setTeacherUuid(modifyTeacher.getTeacherUuid());
        teacher.setTeacherName(modifyTeacher.getTeacherName());
        teacher.setTeacherClass(modifyTeacher.getTeacherClass());
        teacher.setTeacherLevel(modifyTeacher.getTeacherLevel());

        if (modifyTeacher.getModifyTeacherPassword()) {
            Teacher teacher1 = teacherMapper.selectById(modifyTeacher.getTeacherUuid());
            if (teacher1 == null) {
                return Result.fail("Uuid not found.");
            }
            else {
                teacher.setTeacherPassword(MD5Utils.encode(String.valueOf(teacher1.getTeacherId())));
            }
        }

        int i = teacherMapper.updateById(teacher);
        return Result.success(i);
    }

    @Override
    public Result<Object> deleteTeacher(Integer id) {
        int i = teacherMapper.deleteById(id);
        return Result.success(i);
    }

    @Override
    public Result<Object> insertTeacher(String token, TeacherDataDisplay teacherDataDisplay) {
        Teacher teacher = new Teacher();
        teacher.setTeacherName(teacherDataDisplay.getTeacherName());
        teacher.setTeacherClass(teacherDataDisplay.getTeacherClass());
        teacher.setTeacherLevel(teacherDataDisplay.getTeacherLevel());

        int day = IdUtils.getTeacherIdPrefix();
        Integer latest = teacherMapper.selectLatestId(day);
        if (latest == null) {
            latest = Integer.valueOf(day + "000");
        }

        teacher.setTeacherId(IdUtils.generateTeacherId(latest));
        teacher.setTeacherPassword(MD5Utils.encode(String.valueOf(teacher.getTeacherId())));

        int insert = teacherMapper.insert(teacher);
        return Result.success(insert);
    }

    @Override
    public void getTeacherExcel(String token, HttpServletResponse response) {
        List<Teacher> teachers = teacherMapper.selectList(null);
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("Teacher Excel", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Teacher.class).sheet("Student").doWrite(teachers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> uploadTeacherExcel(MultipartFile file) {
        List<Teacher> teachers = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            int available = inputStream.available();
            if (available / 1024 / 1024 > 1) {
                return teachers;
            }
            EasyExcel.read(inputStream, Teacher.class, new PageReadListener<Teacher>(dataList -> {
                int day = IdUtils.getTeacherIdPrefix();

                for (Teacher teacher : dataList) {
                    Integer latest = teacherMapper.selectLatestId(day);

                    if (latest == null) {
                        latest = Integer.valueOf(day + "000");
                    }

                    teacher.setTeacherId(IdUtils.generateTeacherId(latest));
                    teacher.setTeacherPassword(MD5Utils.encode(String.valueOf(teacher.getTeacherId())));

                    teachers.add(teacher);
                }
            })).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return teachers;
    }
}




