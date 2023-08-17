/**
 * 
 */
package com.wibmo.constant;

/**
 * 
 */
public class SQLConstants {
	public static String AUTH_DETAILS = "SELECT * FROM user.auth_creds where user_email=";
	public static String USER_DETAILS = "SELECT * FROM user.auth_creds where user_email=";
	public static String PROFESSOR_COURSE_TAUGHT = "SELECT course_id from user.course WHERE";
	public static String FETCH_REGISTERED_COURSES = "SELECT * from user.registered_courses";
	public static String CREATE_QUERY_GRADE = "INSERT INTO user.report_card (report_id,student_id,course_id,grade,semester,year) VALUES (?,?,?,?,?,?)";
	public static String COURSE_INSERT = "INSERT INTO registered_courses (student_id, courses) values(?, ?)";
	public static String COURSE_DROP = " DELETE FROM registered_courses where student_id=? AND courses=?";
	public static String COURSE_VIEW = " SELECT c.name AS courseName, c.semester FROM course c JOIN registered_courses rc ON c.id = rc.courses where StudentId=?";
	public static String GRADE_VIEW = "SELECT course_id, grade FROM report_card WHERE student_id = ?";
	public static String FETCH_COURSE_BY_ID = "SELECT * FROM user.course "
	+ "WHERE course_id = ";
	public static String FETCH_COURSE_BY_SEMESTER = "SELECT * FROM user.course "
	+ "WHERE semester = ";
	public static String FETCH_COURSE_BY_PROFESSOR_ID = "SELECT * FROM user.course "
	+ "WHERE professor_id = ";
	public static String FETCH_REPORT_CARD_BY_STUDENT_ID = "SELECT * from report_card"
	+"WHERE student_id =?";
	public static String FETCH_REPORT_CARD_BY_REPORT_ID = "SELECT * FROM user.report_card WHERE report_id = ";
	public static String UPDATE_REPORT_CARD_BY_REPORT_ID = "UPDATE user.report_card SET student_id = ? , course_id = ?, grade = ?, semester = ?, year = ? WHERE report_id = ?";
	public static String FIND_PROFESSOR_BY_IDS = "SELECT * FROM professor "
	+ "WHERE professor_id IN(?)";
	public static String FETCH_STUDENT_BY_ID = "SELECT * FROM student "
	+ "WHERE student_id = ?";
	public static String FETCH_USER_BY_IDS = "SELECT * FROM user"
	+ "WHERE user_id IN (?)";
	public static String NOTIFICATION_CREATE = "INSERT INTO user.notifications (notification_to_user,notification_message) VALUES (?,?)";
	public static String NOTIFICATION_FETCH_BY_ID = "SELECT * FROM user.notifications WHERE notification_to_user = ? AND notification_status = 'PENDING'";
	public static String NOTIFICATION_UPDATE_STATUS = "UPDATE user.notifications SET notification_status = 'ACKNOWLEDGED' WHERE notification_id = ?";
}
