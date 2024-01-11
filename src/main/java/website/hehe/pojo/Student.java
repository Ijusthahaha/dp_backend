package website.hehe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @TableName student
 */
@TableName(value = "student")
@Data
public class Student implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer studentUuid;

    private Integer studentId;

    private String studentPassword;

    private String studentName;

    private String studentClass;

    private String studentSex;

    private Integer studentAge;

    @Version
    private Integer version;

    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}