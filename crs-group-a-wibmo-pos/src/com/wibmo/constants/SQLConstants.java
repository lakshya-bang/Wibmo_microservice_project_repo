/**
 * 
 */
package com.wibmo.constants;

/**
 * 
 */
public class SQLConstants {
	public static String AUTH_DETAILS = "SELECT * FROM user.auth_creds where email=";
	public static String USER_DETAILS = "SELECT * FROM user.user_details where email=";
	public static String COURSE_INSERT = "INSERT INTO registered_courses (student_id, courses) values(?, ?)";
	public static String COURSE_DROP = " DELETE FROM registered_courses where student_id=? AND courses=?";
	public static String COURSE_VIEW = " SELECT c.name AS courseName, c.semester FROM course c JOIN registered_courses rc ON c.id = rc.courses where StudentId=?";
	public static String GRADE_VIEW = "SELECT course_id, grade FROM report_card WHERE student_id = ?";
}
