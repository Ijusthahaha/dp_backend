package website.hehe.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LogVo {
    private Integer logId;

    private String logType;

    private String logLocation;

    private Integer dp;

    private Date date;

    private Integer appealId;

    private String remark;

    private Integer studentId;

    private Integer teacherId;

    private Integer appeal;
}
