package website.hehe.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.pojo.vo.LogVo;
import website.hehe.service.LogService;
import website.hehe.utils.Operations;
import website.hehe.utils.annotations.AccessLimit;
import website.hehe.utils.annotations.OperateLog;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class LogController {

    private LogService logService;

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get logs")
    @AccessLimit
    @GetMapping("/getLogs")
    public Result<List<LogVo>> getLogs(@RequestHeader String token) {
        return logService.getLogs(token);
    }

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get logs")
    @AccessLimit
    @GetMapping("/getLogsByStudents")
    public Result<List<LogVo>> getLogsByStudentId(@RequestParam("studentId") Integer studentId) {
        return logService.getLogsByStudentId(studentId);
    }

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get raw logs")
    @AccessLimit
    @GetMapping("/getRawLogs")
    public Result<List<Log>> getRawLogs(@RequestHeader String token) {
        return logService.getRawLogs(token);
    }

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get compared logs")
    @AccessLimit
    @GetMapping("/getCompareLogs")
    public Result<List<Map<String, Object>>> getCompareLogs() {
        return logService.getCompareLogs();
    }

    @OperateLog(operateModel = "Log", operateType = Operations.Insert, operateDesc = "Attempt to post log")
    @AccessLimit
    @PostMapping("/postLogs")
    public Result<Object> postLogs(@RequestHeader String token, @RequestBody LogRequest logRequest) {
        return logService.postLogs(token, logRequest);
    }

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get all logs")
    @AccessLimit
    @GetMapping("/getAllLogs")
    public Result<List<Map<String, Object>>> getAllLogs() {
        return logService.getAllLogs();
    }

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get logs by class")
    @AccessLimit
    @GetMapping("/getAllLogsByClass")
    public Result<List<Map<String, Object>>> getAllLogsByClass(@RequestHeader String token) {
        return logService.getAllLogsByClass(token);
    }

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get total DPs")
    @AccessLimit
    @GetMapping("/getTotalDp")
    public Result<Integer> getTotalDp() {
        Log one = logService.getOne(new QueryWrapper<Log>().select("sum(dp) as dp"));
        if (one == null) {
            return Result.success(0);
        } else {
            one.setDp(one.getDp());
        }
        return Result.success(one.getDp());
    }

//    @OperateLog(operateModel = "Log", operateType = Operations.Get, operateDesc = "Attempt to get yesterday's DP")
    @AccessLimit
    @GetMapping("/getYesterdayDp")
    public Result<Integer> getYesterdayDp() {
        return logService.getYesterdayDp();
    }
}
