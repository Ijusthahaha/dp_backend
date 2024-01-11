package website.hehe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import website.hehe.pojo.Appeal;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【appeal】的数据库操作Mapper
 * @createDate 2023-12-18 18:56:07
 * @Entity website.hehe.pojo.Appeal
 */
public interface AppealMapper extends BaseMapper<Appeal> {

    List<Map<String, Object>> selectAppealByUserId(Integer userId);

    void updateAppealsByAppealId(Integer userId, Integer appealStatus, Integer appealId, String appealReason);
}




