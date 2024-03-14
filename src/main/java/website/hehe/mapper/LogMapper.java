package website.hehe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.LogRequest;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【log】的数据库操作Mapper
 * @createDate 2023-12-14 12:44:01
 * @Entity website.hehe.pojo.Log
 */
public interface LogMapper extends BaseMapper<Log> {

    List<Log> selectByStudentIdAndAppealStatus(Integer userId);

    List<LogRequest> selectLogsByAppealIdExcludeNonPendingAppeals();

    List<Map<String, Object>> selectAllLogsIncludedClassAndStudentDataExcludedFulfilledAppeals();

    List<Map<String, Object>> selectAllLogsIncludedClassAndStudentDataExcludedFulfilledAppealsByClassName(Integer userId);

    Integer getYesterdayDp();
}




