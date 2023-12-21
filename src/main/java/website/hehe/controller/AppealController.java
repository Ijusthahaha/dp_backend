package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import website.hehe.pojo.Appeal;
import website.hehe.pojo.vo.AppealRequest;
import website.hehe.service.AppealService;
import website.hehe.utils.Result;

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
}
