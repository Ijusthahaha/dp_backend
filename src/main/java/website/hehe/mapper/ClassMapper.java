package website.hehe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import website.hehe.pojo.Class;

/**
 * @author hehe
 * @description 针对表【class】的数据库操作Mapper
 * @createDate 2023-12-18 18:55:38
 * @Entity website.hehe.pojo.Class
 */
public interface ClassMapper extends BaseMapper<Class> {

    Integer getClassLevelByStudentClass(String studentClass);

    Integer getClassIdByClassName(String studentClass);
}




