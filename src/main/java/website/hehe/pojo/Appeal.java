package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @TableName appeal
 */
@TableName(value = "appeal")
@Data
public class Appeal implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer appealId;

    private Integer appealStatus;

    private String appealReason;

    @Version
    private Integer version;

    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}