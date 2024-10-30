package website.hehe.interceptor.utils;

public class InterceptorList {
    public static final String[] TOKEN_LOG = {
            "/log",
            "/appeal",
            "/student/changePassword"
    };
    public static final String[] SUPER_OPERATION = {
            "/appeal/getPendingAppeals",
            "/appeal/rejectAppeals",
            "/appeal/fulfillAppeals",
            "/log/getLogsByStudentId"
    };
    public static final String[] ADMIN_OPERATION = {
            "/student/getStudentByUuid",
            "/student/insertStudent",
            "/student/modifyStudent",
            "/student/insertStudent",
            "/student/getStudentExcel",
            "/student/uploadStudentExcel",
            "/student/modifyStudent",
            "/student/getAllStudents",
            "/student/getTopDpStudents",
            "/teacher/getAllTeachers",
            "/teacher/getTeacherByUuid",
            "/teacher/insertTeacher",
            "/teacher/modifyTeacher",
            "/teacher/deleteTeacher",
            "/teacher/getTeacherExcel",
            "/teacher/uploadTeacherExcel",
            "/teacher/getTopDpTeachers",
            "/class/createClass",
            "/class/deleteClass",
            "/class/getClassExcel",
            "/class/uploadClassExcel",
            "/operation/getOperation",
            "/api/ban",
            "/api/unban",
            "/api/isBannedUser",
            "/api/getServerInfo"
    };
}

/* All Apis (Last Update: 2024/5/27)
 * banUser: /api/ban
 * unbanUser: /api/unban
 * isBannedUser: /api/isBanned
 * getServerInfo: /api/info
 * getOperation: /operation/getOperation
 * getCompareLogs: /log/getCompareLogs
 * getRawLogs: /log/getRawLogs
 * getLogs: /log/getLogs
 * postLogs: /log/postLogs
 * getAllLogsByClass: /log/getAllLogsByClass
 * createClass: /class/createClass
 * deleteClass: /class/deleteClass
 * exportClassTable: /class/getClassExcel
 * importClassTable: /class/uploadClassExcel
 * getAllTeachers: /teacher/getAllTeachers
 * getTeacherByUuid: /teacher/getTeacherByUuid
 * insertTeacher: /teacher/insertTeacher
 * modifyTeacher: /teacher/modifyTeacher
 * deleteTeacher: /teacher/deleteTeacher
 * exportTeacherTable: /teacher/getTeacherExcel
 * importTeacherTable: /teacher/uploadTeacherExcel
 * getTopDpTeachers: /teacher/getTopDpTeachers
 * checkTeacherLogin: /teacher/checkLogin
 * getAppeals: /appeal/getAppeals
 * createAppeals: /appeal/createAppeals
 * getPendingAppeals: /appeal/getPendingAppeals
 * rejectAppeals: /appeal/rejectAppeals
 * fulfillAppeals: /appeal/fulfillAppeals
 * getStudentByUuid: /student/getStudentByUuid
 * insertStudent: /student/insertStudent
 * modifyStudent: /student/modifyStudent
 * changeStudentPassword: /student/changePassword
 * getTopDpStudents: /student/getTopDpStudents
 * checkStudentLogin: /student/checkLogin
 * exportStudentTable: /student/getStudentExcel
 * importStudentTable: /student/uploadStudentExcel
 */