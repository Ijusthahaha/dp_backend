package website.hehe.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.mapper.ClassMapper;
import website.hehe.mapper.StudentMapper;
import website.hehe.pojo.Class;
import website.hehe.pojo.Student;
import website.hehe.pojo.vo.ModifyStudent;
import website.hehe.pojo.vo.StudentDataDisplay;
import website.hehe.service.RedisService;
import website.hehe.service.StudentService;
import website.hehe.utils.*;
import website.hehe.utils.result.Result;
import website.hehe.utils.result.ResultEnum;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static website.hehe.utils.getDataUtils.getLatestInteger;

/**
 * @author hehe
 * @description 针对表【student】的数据库操作Service实现
 * @createDate 2023-12-14 13:49:21
 */
@Service
@Setter(onMethod_ = @Autowired)
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private StudentMapper studentMapper;
    private ClassMapper classMapper;
    private JwtUtils jwtUtils;
    private StringRedisTemplate redisTemplate;
    private RedisService redisService;

    @Override
    public Result<Map<String, String>> login(Student student) {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Student::getStudentId, student.getStudentId());
        Student loginUser = studentMapper.selectOne(lambdaQueryWrapper);
        if (loginUser == null) {
            return Result.build(ResultEnum.PASSWORD_ERROR, null);
        }

        if (loginUser.getIsExpired() == 1) {
            return Result.build(ResultEnum.ACCOUNT_EXPIRED, null);
        }

        if (redisService.isBanned("student", String.valueOf(loginUser.getStudentUuid()))) {
            return Result.build(ResultEnum.ACCOUNT_BANNED, null);
        }

        if (!StringUtils.isEmpty(student.getStudentPassword()) && MD5Utils.encode(student.getStudentPassword()).equals(loginUser.getStudentPassword())) {
            String token = jwtUtils.createToken(student.getStudentId(), "student");
            Map<String, String> data = new HashMap<>();
            data.put("token", token);

            redisTemplate.opsForValue().increment("tv");
            return Result.success(data);
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

            if (student.getIsExpired() == 1) {
                return Result.build(ResultEnum.ACCOUNT_EXPIRED, null);
            }

            Map<String, String> map = new HashMap<>();
            map.put("studentUuid", String.valueOf(student.getStudentId()));
            map.put("studentName", student.getStudentName());
            map.put("studentClass", student.getStudentClass());

            try {
                Integer classLevelByStudentClass = classMapper.getClassLevelByStudentClass(student.getStudentClass());
                map.put("studentLevel", ClassLevelUtils.parseClassLevel(classLevelByStudentClass).getLevel());
            } catch (Exception e) {
                map.put("studentLevel", "-1");
            }

            return Result.success(map);
        }
        return Result.build(ResultEnum.NOTLOGIN, null);
    }

    @Override
    public Result<Object> changePassword(String token, String password) {
        Integer userId = jwtUtils.getUserId(token);

        if (userId == null) {
            return Result.fail("JWT Expired.");
        }
        Student student = new Student();
        student.setStudentPassword(MD5Utils.encode(password));

        int update = studentMapper.update(student, new UpdateWrapper<Student>().eq("student_id", userId));
        String newToken = jwtUtils.createToken(userId, "student");

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", newToken);
        responseData.put("result", update);
        return Result.success(responseData);
    }

    @Override
    public Result<List<StudentDataDisplay>> getAllStudents() {
        List<StudentDataDisplay> students = studentMapper.selectAllStudentsWithClassLevel();
        return Result.success(students);
    }

    @Override
    public Result<List<Student>> getStudents(String keyword) {
        List<Student> students = studentMapper.selectStudentsByKeyword(keyword);
        return Result.success(students);
    }

    @Override
    public Result<List<StudentDataDisplay>> getAllClassStudents(String className) {
        if (className != null && !className.isEmpty()) {
            return Result.success(studentMapper.selectAllStudentsByClassName(className));
        } else {
            return Result.success(studentMapper.selectAllEmptyClassStudent());
        }
    }

    @Override
    @Deprecated
    public Result<List<String>> getAllClasses() {
        List<Class> classes = classMapper.selectList(new QueryWrapper<Class>().lambda().orderBy(true, true, Class::getClassLevel));
        List<String> collect = classes.stream().map(Class::getClassName).toList();
        return Result.success(collect);
    }

    @Override
    public Result<Object> insertStudent(String token, StudentDataDisplay studentDataDisplay) {
        // we dont need to verify token right?
        Student student = new Student();
        student.setStudentName(studentDataDisplay.getStudentName());
        student.setStudentClass(studentDataDisplay.getStudentClass());
        student.setStudentAge(studentDataDisplay.getStudentAge());
        student.setStudentSex(studentDataDisplay.getStudentSex());

        Calendar rightNow = Calendar.getInstance();
        int i = rightNow.get(Calendar.YEAR);

        Integer classId = classMapper.getClassIdByClassName(student.getStudentClass());
        Integer latest = studentMapper.selectLatestId(i, classId);

        if (latest == null) {
            latest = Integer.valueOf(i + Integer.toString(classId) + "000");
        }

        if (classId == null) {
            return Result.fail("Class not found.");
        }

        Integer generateStudentId = IdUtils.generateStudentId(i, latest, classId);
        student.setStudentId(generateStudentId);

        // set students' default password as studentId.
        student.setStudentPassword(MD5Utils.encode(String.valueOf(generateStudentId)));

        int insert = studentMapper.insert(student);
        return Result.success(insert);
    }

    @Override
    public void getStudentExcel(String token, HttpServletResponse response) {
        List<Student> students = studentMapper.selectList(null);
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("Student Excel", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Student.class).sheet("Student").doWrite(students);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> uploadStudentExcel(MultipartFile file) {
        List<Student> students = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            int available = inputStream.available();
            if (available / 1024 / 1024 > 1) {
                return students;
            }
            EasyExcel.read(inputStream, Student.class, new PageReadListener<Student>(dataList -> {
                Calendar rightNow = Calendar.getInstance();
                int i = rightNow.get(Calendar.YEAR);
                Map<String, Integer> latestMap = new HashMap<>();

                for (Student student : dataList) {
                    if (student.getStudentClass() != null) {
                        Integer classId = classMapper.getClassIdByClassName(student.getStudentClass());
                        if (classId != null) {
                            Integer latest = latestMap.getOrDefault(student.getStudentClass(), null);
                            latest = getLatestInteger(i, latest, classId, student, studentMapper);
                            latestMap.put(student.getStudentClass(), latest + 1);
                        }
                    }
                    students.add(student);
                }
            })).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return students;
    }

    @Override
    public Result<Object> modifyStudent(ModifyStudent modifyStudent) {
        Student student = new Student();
        student.setStudentUuid(modifyStudent.getStudentUuid());
        student.setStudentName(modifyStudent.getStudentName());
        student.setStudentClass(modifyStudent.getStudentClass());
        student.setStudentSex(modifyStudent.getStudentSex());
        student.setStudentAge(modifyStudent.getStudentAge());
        student.setIsExpired(modifyStudent.getIsExpired());

        if (modifyStudent.getModifyStudentId() || modifyStudent.getModifyStudentPassword()) {
            if (modifyStudent.getStudentClass().isEmpty()) {
                return Result.fail(null);
            } else {
                Calendar rightNow = Calendar.getInstance();
                int i = rightNow.get(Calendar.YEAR);

                Integer classId = classMapper.getClassIdByClassName(modifyStudent.getStudentClass());
                Integer latest = studentMapper.selectLatestId(i, classId);

                if (latest == null) {
                    latest = Integer.valueOf(i + Integer.toString(classId) + "000");
                }
                Integer generateStudentId = IdUtils.generateStudentId(i, latest, classId);
                if (modifyStudent.getModifyStudentId()) {
                    student.setStudentId(generateStudentId);
                }
                if (modifyStudent.getModifyStudentPassword()) {
                    Student student1 = studentMapper.selectById(modifyStudent.getStudentUuid());
                    Integer studentId = student1.getStudentId();

                    if (studentId != null) {
                        // init password only
                        student.setStudentPassword(MD5Utils.encode(String.valueOf(studentId)));
                    } else {
                        // init id & password
                        student.setStudentId(generateStudentId);
                        student.setStudentPassword(MD5Utils.encode(String.valueOf(generateStudentId)));
                    }
                }
            }
        }
        int i = studentMapper.updateById(student);
        return Result.success(i);
    }

    @Override
    public Result<List<Map<String, Integer>>> getTopDpStudents() {
        return Result.success(studentMapper.getTopDpStudents());
    }
}