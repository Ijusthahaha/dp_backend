package website.hehe.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.*;
import website.hehe.service.RedisService;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Setter(onMethod_ = @Autowired)
public class RedisController {
    private RedisService redisService;
    private HealthEndpoint healthEndpoint;
    private MetricsEndpoint metricsEndpoint;

    @GetMapping("/uv")
    public String uv() {
        return redisService.uv();
    }

    @PutMapping("/ban")
    public boolean banUser(@RequestBody Map<String, Object> user) {
        return redisService.banUser((String) user.get("type"), (String) user.get("uuid"), (Integer) user.get("duration"));
    }

    @GetMapping("/isBanned")
    public boolean isBanned(@RequestParam Map<String, String> user) {
        return redisService.isBanned(user.get("type"), user.get("uuid"));
    }

    @PutMapping("/unban")
    public void unbanUser(@RequestBody Map<String, String> user) {
        redisService.unBanUser(user.get("type"), user.get("uuid"));
    }

    @GetMapping("/getAllBannedUser")
    public Set<String> getAllBannedUser() {
        return redisService.getAllBannedUser();
    }

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