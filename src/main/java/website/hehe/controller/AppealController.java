package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.vo.AppealRequest;
import website.hehe.pojo.vo.LogRequest;
import website.hehe.pojo.vo.ModifyAppeal;
import website.hehe.service.AppealService;
import website.hehe.utils.result.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appeal")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class AppealController {

    private AppealService appealService;

    @GetMapping("/getAppeals")
    public Result<List<Map<String, Object>>> getAppeals(@RequestHeader String token) {
        return appealService.getAppeals(token);
    }

    @PostMapping("/createAppeals")
    public Result<Object> createAppeals(@RequestHeader String token, @RequestBody AppealRequest appealRequest) {
        return appealService.createAppeals(token, appealRequest);
    }

    @GetMapping("/getPendingAppeals")
    public Result<List<LogRequest>> getPendingAppeals(@RequestHeader String token) {
        return appealService.getPendingAppeals(token);
    }

    @PutMapping("/rejectAppeals")
    public Result<Object> rejectAppeals(@RequestHeader String token, @RequestBody ModifyAppeal modifyAppeal) {
        return appealService.rejectAppeals(token, modifyAppeal);
    }

    @PutMapping("/fulfillAppeals")
    public Result<Object> fulfillAppeals(@RequestHeader String token, @RequestBody ModifyAppeal modifyAppeal) {
        return appealService.fulfillAppeals(token, modifyAppeal);
    }
}
