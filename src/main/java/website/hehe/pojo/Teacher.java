package website.hehe.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import website.hehe.converters.TeacherLevelConverter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @TableName teacher
 */
@TableName(value = "teacher")
@Data
public class Teacher implements Serializable {
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Integer teacherUuid;

    @ColumnWidth(20)
    @ExcelProperty("Teacher ID")
    private Integer teacherId;

    @ColumnWidth(20)
    @ExcelProperty("Teacher Name")
    private String teacherName;

    @ExcelIgnore
    private String teacherPassword;

    @ColumnWidth(20)
    @ExcelProperty("Teacher Class")
    private String teacherClass;

    @ColumnWidth(20)
    @ExcelProperty(value = "Teacher Level", converter = TeacherLevelConverter.class)
    private Integer teacherLevel;

    @Version
    @ExcelIgnore
    private Integer version;

    @ExcelIgnore
    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}