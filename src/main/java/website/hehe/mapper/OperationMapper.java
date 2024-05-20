package website.hehe.mapper;

import website.hehe.pojo.Operation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hehe
* @description 针对表【operation】的数据库操作Mapper
* @createDate 2024-05-12 14:54:39
* @Entity website.hehe.pojo.Operation
*/
public interface OperationMapper extends BaseMapper<Operation> {
    List<Operation> selectByTime(String startTime, String endTime);
}




