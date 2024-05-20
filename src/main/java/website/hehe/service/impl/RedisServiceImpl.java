package website.hehe.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import website.hehe.service.RedisService;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Setter(onMethod_ = @Autowired)
public class RedisServiceImpl implements RedisService {

    private StringRedisTemplate redisTemplate;

    @Override
    public String uv() {
        return redisTemplate.opsForValue().get("tv");
    }

    @Override
    public boolean banUser(String type, String uuid, Integer duration) {
        if (type == null || uuid == null || duration == null) return false;
        if (Objects.equals(type, "student")) {
            return banStudent(uuid, duration);
        } else if (Objects.equals(type, "teacher")) {
            return banTeacher(uuid, duration);
        }
        return false;
    }

    @Override
    public boolean isBanned(String type, String uuid) {
        if (Objects.equals(type, "student")) {
            return Boolean.TRUE.equals(redisTemplate.hasKey("banned:student:" + uuid));
        } else if (Objects.equals(type, "teacher")) {
            return Boolean.TRUE.equals(redisTemplate.hasKey("banned:teacher:" + uuid));
        }
        return false;
    }

    @Override
    public void unBanUser(String type, String uuid) {
        if (Objects.equals(type, "student")) {
            redisTemplate.delete("banned:student:" + uuid);
        } else if (Objects.equals(type, "teacher")) {
            redisTemplate.delete("banned:teacher:" + uuid);
        }
    }

    @Override
    public Set<String> getAllBannedUser() {
        return redisTemplate.keys("banned:*");
    }

    private boolean banStudent(String uuid, Integer duration) {
        long unbanTime = getCurrentTime(duration) * 1000;
        redisTemplate.opsForValue().set("banned:student:" + uuid, Long.toString(unbanTime), unbanTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return true;
    }

    private boolean banTeacher(String uuid, Integer duration) {
        long unbanTime = getCurrentTime(duration) * 1000;
        redisTemplate.opsForValue().set("banned:teacher:" + uuid, Long.toString(unbanTime), unbanTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return true;
    }

    private Long getCurrentTime(Integer day) {
        return Instant.now().getEpochSecond() + (day * 24 * 60 * 60);
    }
}
