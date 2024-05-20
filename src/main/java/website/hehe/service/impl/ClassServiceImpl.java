package website.hehe.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import website.hehe.mapper.ClassMapper;
import website.hehe.mapper.StudentMapper;
import website.hehe.pojo.Class;
import website.hehe.pojo.Student;
import website.hehe.pojo.vo.ClassDataDisplay;
import website.hehe.service.ClassService;
import website.hehe.utils.result.Result;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static website.hehe.utils.getDataUtils.getLatestInteger;

/**
 * @author hehe
 * @description 针对表【class】的数据库操作Service实现
 * @createDate 2023-12-18 18:55:38
 */
@Service
@Setter(onMethod_ = @Autowired)
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    private ClassMapper classMapper;
    private StudentMapper studentMapper;

    @Override
    public Result<List<Class>> getAllClasses() {
        List<Class> classes = classMapper.selectList(new QueryWrapper<Class>().lambda().orderBy(true, true, Class::getClassLevel));
        return Result.success(classes);
    }

    @Override
    public Result<Map<String, Number>> getClassData(Integer classId) {
        Map<String, Number> map = classMapper.selectClassDataByClassId(classId);
        return Result.success(map);
    }

    @Override
    public Result<Object> createClass(ClassDataDisplay classDataDisplay) {
        Class aClass = new Class();
        aClass.setClassName(classDataDisplay.getClassName());
        aClass.setClassLevel(classDataDisplay.getClassLevel());
        int insert = classMapper.insert(aClass);
        return Result.success(insert);
    }

    @Transactional
    @Override
    public Result<Object> deleteClass(Integer classId) {
        // set student and teachers' class as null
        classMapper.setStudentClassAsNone(classId);
        classMapper.setTeacherClassAsNone(classId);

        // delete class
        int i = classMapper.deleteById(classId);
        return Result.success(i);
    }

    @Override
    public void getClassExcel(HttpServletResponse response) {

    }

    @Override
    public List<Student> uploadClassExcel(MultipartFile file, String className) {
        List<Student> classes = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            int available = inputStream.available();
            if (available / 1024 / 1024 > 1) {
                return classes;
            }
            EasyExcel.read(inputStream, Student.class, new PageReadListener<Student>(dataList -> {
                Calendar rightNow = Calendar.getInstance();
                int i = rightNow.get(Calendar.YEAR);
                Integer latest = null;
                Integer classId = classMapper.getClassIdByClassName(className);
                if (classId == null) return;

                for (Student student : dataList) {
                    latest = getLatestInteger(i, latest, classId, student, studentMapper);
                    student.setStudentClass(className);
                    classes.add(student);
                    latest++;
                }
            })).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return classes;
    }
}