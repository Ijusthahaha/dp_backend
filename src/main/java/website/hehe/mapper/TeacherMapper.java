package website.hehe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import website.hehe.pojo.Teacher;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【teacher】的数据库操作Mapper
 * @createDate 2023-12-14 15:46:27
 * @Entity website.hehe.pojo.Teacher
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    Integer selectLatestId(int i);

    List<Map<String, Integer>> getTopDpTeachers();
}




