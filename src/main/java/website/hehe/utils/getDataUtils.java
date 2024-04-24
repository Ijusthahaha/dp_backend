package website.hehe.utils;

import website.hehe.mapper.StudentMapper;
import website.hehe.pojo.Student;

public class getDataUtils {
    public static Integer getLatestInteger(int i, Integer latest, Integer classId, Student student, StudentMapper studentMapper) {
        if (latest == null) {
            latest = studentMapper.selectLatestId(i, classId);
            if (latest == null) {
                latest = Integer.valueOf(i + Integer.toString(classId) + "000");
            }
        }
        Integer generateStudentId = IdUtils.generateStudentId(i, latest, classId);
        student.setStudentId(generateStudentId);
        student.setStudentPassword(MD5Utils.encode(String.valueOf(generateStudentId)));
        return latest;
    }
}
