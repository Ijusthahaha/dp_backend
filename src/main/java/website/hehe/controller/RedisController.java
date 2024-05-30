package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.*;
import website.hehe.service.RedisService;
import website.hehe.utils.Operations;
import website.hehe.utils.annotations.OperateLog;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class RedisController {
    private RedisService redisService;
    private HealthEndpoint healthEndpoint;
    private MetricsEndpoint metricsEndpoint;

    @OperateLog(operateModel = "Api", operateType = Operations.Get, operateDesc = "Attempt to get uv")
    @GetMapping("/uv")
    public String uv() {
        return redisService.uv();
    }

    @OperateLog(operateModel = "Api", operateType = Operations.Ban, operateDesc = "Attempt to ban user")
    @PutMapping("/ban")
    public boolean banUser(@RequestBody Map<String, Object> user) {
        return redisService.banUser((String) user.get("type"), (String) user.get("uuid"), (Integer) user.get("duration"));
    }

    @OperateLog(operateModel = "Api", operateType = Operations.Ban, operateDesc = "Attempt to check user's ban status")
    @GetMapping("/isBanned")
    public boolean isBanned(@RequestParam Map<String, String> user) {
        return redisService.isBanned(user.get("type"), user.get("uuid"));
    }

    @OperateLog(operateModel = "Api", operateType = Operations.Ban, operateDesc = "Attempt to unban user")
    @PutMapping("/unban")
    public void unbanUser(@RequestBody Map<String, String> user) {
        redisService.unBanUser(user.get("type"), user.get("uuid"));
    }

    @OperateLog(operateModel = "Api", operateType = Operations.Get, operateDesc = "Attempt to get all banned user")
    @GetMapping("/getAllBannedUser")
    public Set<String> getAllBannedUser() {
        return redisService.getAllBannedUser();
    }

    @OperateLog(operateModel = "Api", operateType = Operations.Get, operateDesc = "Attempt to get server info")
    @GetMapping("/info")
    public Map<String, Object> getHealth() {
        Map<String, Object> map = new HashMap<>();
        map.put("health", healthEndpoint.health());
        List<MetricsEndpoint.MetricDescriptor> list = new ArrayList<>();
        list.add(metricsEndpoint.metric("application.ready.time", null));
        list.add(metricsEndpoint.metric("jvm.info", null));
        list.add(metricsEndpoint.metric("jvm.memory.max", null));
        list.add(metricsEndpoint.metric("jvm.memory.used", null));
        map.put("metrics", list);
        return map;
    }
}