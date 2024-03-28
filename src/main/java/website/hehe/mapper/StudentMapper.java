package website.hehe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import website.hehe.pojo.Student;
import website.hehe.pojo.vo.StudentDataDisplay;

import java.util.List;

/**
 * @author hehe
 * @description 针对表【student】的数据库操作Mapper
 * @createDate 2023-12-14 14:03:34
 * @Entity website.hehe.pojo.Student
 */

public interface StudentMapper extends BaseMapper<Student> {

    List<StudentDataDisplay> selectAllStudentsWithClassLevel();

    List<Student> selectStudentsByKeyword(String keyword);

    List<StudentDataDisplay> selectAllStudentsByClassName(String className);

    Integer selectLatestId(Integer year, Integer classId);

    List<StudentDataDisplay> selectAllEmptyClassStudent();
}




