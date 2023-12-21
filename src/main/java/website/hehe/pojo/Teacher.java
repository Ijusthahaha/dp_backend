package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer teacherUuid;

    private Integer teacherId;

    private String teacherName;

    private String teacherPassword;

    private String teacherClass;

    private Integer teacherLevel;

    @Version
    private Integer version;

    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}