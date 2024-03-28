package website.hehe.pojo.vo;

import lombok.Data;

@Data
public class ModifyTeacher {
    private Integer teacherUuid;
    private String teacherName;
    private String teacherClass;
    private Integer teacherLevel;

    private Boolean modifyTeacherPassword;
}
