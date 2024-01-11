package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @TableName log
 */
@TableName(value = "log")
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

    private Integer teacherId;

    @Version
    private Integer version;

    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}