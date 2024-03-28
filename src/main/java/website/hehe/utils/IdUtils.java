package website.hehe.utils;

import java.util.Calendar;

public class IdUtils {
    public static Integer generateStudentId(int i, int latest, int classId) {
        // student id format: YYYYCCCII e.g. 202130109 C: class id, I: id

        String filledClassId = String.format("%03d", classId);
        String subLatest = Integer.toString(latest).substring(7);
        Integer nextId = Integer.parseInt(subLatest) + 1;
        String filledId = String.format("%02d", nextId);

        return Integer.valueOf(Integer.toString(i).concat(filledClassId).concat(filledId));
    }

    public static Integer generateTeacherId(int i) {
        String subPrefix = Integer.toString(i).substring(0, 6);
        String subLatest = Integer.toString(i).substring(6);
        Integer nextId = Integer.parseInt(subLatest) + 1;
        String filledId = String.format("%03d", nextId);

        return Integer.valueOf(subPrefix.concat(filledId));
    }

    public static Integer getTeacherIdPrefix() {
        Calendar rightNow = Calendar.getInstance();
        int i = rightNow.get(Calendar.YEAR);
        int j = rightNow.get(Calendar.MONTH);

        int day;
        if (j > 10) {
            day = Integer.parseInt(i + String.valueOf(j));
        } else {
            day = Integer.parseInt(i + "0" + j);
        }
        return day;
    }
}
