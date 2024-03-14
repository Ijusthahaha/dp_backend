package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @TableName class
 */
@TableName(value = "class")
@Data
public class Class implements Serializable {

    @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    private String className;

    private Integer classLevel;

    @Serial
    private static final long serialVersionUID = 1L;
}