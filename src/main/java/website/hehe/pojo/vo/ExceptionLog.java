package website.hehe.pojo.vo;

import lombok.Data;

@Data
public class ExceptionLog {
    private String requestParam;
    private String name;
    private String message;
    private Integer userId;
    private String method;
    private String uri;
}
