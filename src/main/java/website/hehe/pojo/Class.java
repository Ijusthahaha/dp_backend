package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName class
 */
@TableName(value = "class")
@Data
public class Class implements Serializable {
    private Integer classId;

    private String className;

    private Integer classLevel;

    private static final long serialVersionUID = 1L;
}