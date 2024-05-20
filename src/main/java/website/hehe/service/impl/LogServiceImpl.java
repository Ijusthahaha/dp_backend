package website.hehe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.hehe.mapper.LogMapper;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.pojo.vo.LogVo;
import website.hehe.service.LogService;
import website.hehe.utils.JwtUtils;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author hehe
 * @description 针对表【log】的数据库操作Service实现
 * @createDate 2023-12-14 12:44:01
 */
@Service
@Setter(onMethod_ = @Autowired)
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    private LogMapper logMapper;
    private JwtUtils jwtUtils;

    @Override
    public Result<List<LogVo>> getLogs(String token) {
        Integer userId = jwtUtils.getUserId(token);

        List<LogVo> logs = logMapper.selectByStudentIdAndAppealStatus(userId);
        return Result.success(logs);
    }

    @Override
    public Result<List<Log>> getRawLogs(String token) {
        Integer userId = jwtUtils.getUserId(token);

        LambdaQueryWrapper<Log> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Log::getStudentId, userId);
        List<Log> logs = logMapper.selectList(lambdaQueryWrapper);
        return Result.success(logs);
    }

    @Override
    public Result<List<Map<String, Object>>> getCompareLogs() {
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Log> select = queryWrapper.select("log_type logName", "AVG(dp) logDP").groupBy("log_type");
        List<Map<String, Object>> logs = logMapper.selectMaps(select);

        return Result.success(logs);
    }

    @Override
    public Result<Object> postLogs(String token, LogRequest logRequest) {
        Integer userId = jwtUtils.getUserId(token);
        Log log = new Log();
        log.setLogType(logRequest.getType());
        log.setLogLocation(logRequest.getLocation());
        log.setDp(logRequest.getDp());
        log.setDate(logRequest.getDate());
        log.setRemark(logRequest.getReason());
        log.setStudentId(logRequest.getId());
        log.setTeacherId(userId);
        int insert = logMapper.insert(log);
        return Result.success(insert);
    }

    @Override
    public Result<List<Map<String, Object>>> getAllLogs() {
        List<Map<String, Object>> result = logMapper.selectAllLogsIncludedClassAndStudentDataExcludedFulfilledAppeals();
        return Result.success(result);
    }

    @Override
    public Result<List<Map<String, Object>>> getAllLogsByClass(String token) {
        Integer userId = jwtUtils.getUserId(token);
        List<Map<String, Object>> result = logMapper.selectAllLogsIncludedClassAndStudentDataExcludedFulfilledAppealsByClassName(userId);
        return Result.success(result);
    }

    @Override
    public Result<Integer> getYesterdayDp() {
        return Result.success(logMapper.getYesterdayDp());
    }
}




