package website.hehe.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
    @ExcelIgnore
    private Integer studentUuid;

    @ExcelProperty("Student id")
    @ColumnWidth(20)
    private Integer studentId;

    @ExcelIgnore
    private String studentPassword;

    @ExcelProperty("Student name")
    @ColumnWidth(20)
    private String studentName;

    @ExcelProperty("Student class")
    @ColumnWidth(20)
    private String studentClass;

    @ExcelProperty("Student sex")
    @ColumnWidth(20)
    private String studentSex;

    @ExcelProperty("Student age")
    @ColumnWidth(20)
    private Integer studentAge;

    @ExcelIgnore
    private Integer isExpired;

    @Version
    @ExcelIgnore
    private Integer version;

    @ExcelIgnore
    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}