package website.hehe.pojo.vo;

import lombok.Data;

@Data
public class ModifyStudent {
    private Integer studentUuid;
    private String studentName;
    private String studentClass;
    private String studentSex;
    private Integer studentAge;
    private Integer isExpired;

    private Boolean modifyStudentId;
    private Boolean modifyStudentPassword;
}
