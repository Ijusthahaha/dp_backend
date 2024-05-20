package website.hehe.service;

import java.util.Set;

public interface RedisService {
    String uv();

    boolean banUser(String type, String uuid, Integer duration);

    boolean isBanned(String type, String uuid);

    void unBanUser(String type, String uuid);

    Set<String> getAllBannedUser();
}
