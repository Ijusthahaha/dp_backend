package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName operation
 */
@TableName(value ="operation")
@Data
public class Operation implements Serializable {
    private String id;

    private String module;

    private String type;

    private String description;

    private String requestParam;

    private String responseParam;

    private Integer userId;

    private String method;

    private String uri;

    private Date createTime;

    @Version
    private Integer version;

    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}