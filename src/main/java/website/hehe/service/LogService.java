package website.hehe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.pojo.vo.LogVo;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【log】的数据库操作Service
 * @createDate 2023-12-14 12:44:01
 */
public interface LogService extends IService<Log> {

    Result<List<LogVo>> getLogs(String token);

    Result<List<LogVo>> getLogsByStudentId(Integer studentId);

    Result<List<Map<String, Object>>> getCompareLogs();

    Result<List<Log>> getRawLogs(String token);

    Result<Object> postLogs(String token, LogRequest logRequest);

    Result<List<Map<String, Object>>> getAllLogs();

    Result<List<Map<String, Object>>> getAllLogsByClass(String token);

    Result<Integer> getYesterdayDp();
}
