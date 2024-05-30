package website.hehe.controller;

import org.springframework.web.bind.annotation.*;
import website.hehe.utils.annotations.AccessLimit;
import website.hehe.utils.result.Result;

@CrossOrigin
@RestController
@RequestMapping("/")
public class PingController {
    @AccessLimit
    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("pong");
    }
}
