package website.hehe.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LogRequest {
    private Integer id;
    private String studentName;
    private Date date;
    private String location;
    private String type;
    private String reason;
    private Integer dp;
    private String studentClass;
}
