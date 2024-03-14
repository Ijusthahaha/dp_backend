package website.hehe.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.service.LogService;
import website.hehe.utils.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/log")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class LogController {

    private LogService logService;

    @GetMapping("/getLogs")
    public Result<List<Log>> getLogs(@RequestHeader String token) {
        return logService.getLogs(token);
    }

    @GetMapping("/getRawLogs")
    public Result<List<Log>> getRawLogs(@RequestHeader String token) {
        return logService.getRawLogs(token);
    }

    @GetMapping("/getCompareLogs")
    public Result<List<Map<String, Object>>> getCompareLogs() {
        return logService.getCompareLogs();
    }

    @PostMapping("/postLogs")
    public Result<Object> postLogs(@RequestHeader String token, @RequestBody LogRequest logRequest) {
        return logService.postLogs(token, logRequest);
    }

    @GetMapping("/getAllLogs")
    public Result<List<Map<String, Object>>> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/getAllLogsByClass")
    public Result<List<Map<String, Object>>> getAllLogsByClass(@RequestHeader String token) {
        return logService.getAllLogsByClass(token);
    }

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

    @GetMapping("/getYesterdayDp")
    public Result<Integer> getYesterdayDp() {
        return logService.getYesterdayDp();
    }
}
