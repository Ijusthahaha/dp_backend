package website.hehe.mapper;

import website.hehe.pojo.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hehe
* @description 针对表【log】的数据库操作Mapper
* @createDate 2023-12-14 12:44:01
* @Entity website.hehe.pojo.Log
*/
public interface LogMapper extends BaseMapper<Log> {

    List<Log> selectByStudentIdAndAppealStatus(Integer userId);
}




