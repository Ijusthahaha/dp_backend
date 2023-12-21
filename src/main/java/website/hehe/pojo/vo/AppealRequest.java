package website.hehe.pojo.vo;

import lombok.Data;

@Data
public class AppealRequest {
    private Integer appealId;
    private String appealReason;
    private Integer logId;
}
