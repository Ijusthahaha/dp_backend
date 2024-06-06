package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.vo.AppealRequest;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.pojo.vo.ModifyAppeal;
import website.hehe.service.AppealService;
import website.hehe.utils.Operations;
import website.hehe.utils.annotations.AccessLimit;
import website.hehe.utils.annotations.OperateLog;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appeal")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class AppealController {

    private AppealService appealService;

//    @OperateLog(operateModel = "Appeal", operateType = Operations.Get, operateDesc = "Attempt to get appeals")
    @AccessLimit
    @GetMapping("/getAppeals")
    public Result<List<Map<String, Object>>> getAppeals(@RequestHeader String token) {
        return appealService.getAppeals(token);
    }

    @OperateLog(operateModel = "Appeal", operateType = Operations.Insert, operateDesc = "Attempt to create appeals")
    @AccessLimit
    @PostMapping("/createAppeals")
    public Result<Object> createAppeals(@RequestHeader String token, @RequestBody AppealRequest appealRequest) {
        return appealService.createAppeals(token, appealRequest);
    }

//    @OperateLog(operateModel = "Appeal", operateType = Operations.Get, operateDesc = "Attempt to get pending appeals")
    @AccessLimit
    @GetMapping("/getPendingAppeals")
    public Result<List<LogRequest>> getPendingAppeals(@RequestHeader String token) {
        return appealService.getPendingAppeals(token);
    }

    @OperateLog(operateModel = "Appeal", operateType = Operations.Get, operateDesc = "Attempt to get rejected appeals")
    @AccessLimit
    @PutMapping("/rejectAppeals")
    public Result<Object> rejectAppeals(@RequestHeader String token, @RequestBody ModifyAppeal modifyAppeal) {
        return appealService.rejectAppeals(token, modifyAppeal);
    }

    @OperateLog(operateModel = "Appeal", operateType = Operations.Get, operateDesc = "Attempt to get fulfilled appeals")
    @AccessLimit
    @PutMapping("/fulfillAppeals")
    public Result<Object> fulfillAppeals(@RequestHeader String token, @RequestBody ModifyAppeal modifyAppeal) {
        return appealService.fulfillAppeals(token, modifyAppeal);
    }
}
