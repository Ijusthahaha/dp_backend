package website.hehe.pojo.vo;

import lombok.Data;

@Data
public class StudentDataDisplay {
    private String studentUuid;
    private String studentId;
    private String studentName;
    private String studentClass;
    private Integer studentAge;
    private String studentSex;
    private Integer studentClassLevel;
    private Integer dp;
    private Integer appealedCount;
    private Integer isExpired;
}