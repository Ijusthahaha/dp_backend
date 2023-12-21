package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Appeal;
import website.hehe.pojo.Log;
import website.hehe.pojo.vo.CompareLog;
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
}
