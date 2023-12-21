package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * @TableName appeal
 */
@TableName(value ="appeal")
@Data
public class Appeal implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer appealId;

    private Integer appealStatus;

    private String appealReason;

    @Version
    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}