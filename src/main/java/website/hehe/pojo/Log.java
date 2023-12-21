package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName log
 */
@TableName(value ="log")
@Data
public class Log implements Serializable {
    @TableId
    private Integer logId;

    private String logType;

    private String logLocation;

    private Integer dp;

    private Date date;

    private Integer appealId;

    private String remark;

    private Integer studentId;

    @Version
    private Integer version;

    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}