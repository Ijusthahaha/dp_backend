package website.hehe.utils;

public class IdUtils {
    public static Integer generateStudentId(int i, int latest, int classId) {
        // student id format: YYYYCCCII e.g. 202130109 C: class id, I: id

        String filledClassId = String.format("%03d", classId);
        String subLatest = Integer.toString(latest).substring(7);
        Integer nextId = Integer.parseInt(subLatest) + 1;
        String filledId = String.format("%02d", nextId);

        Integer result = Integer.valueOf(Integer.toString(i).concat(filledClassId).concat(filledId));
        System.out.println(result);
        return result;
    }
}
