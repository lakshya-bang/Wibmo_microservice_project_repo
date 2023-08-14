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
	public static String PROFESSOR_COURSE_TAUGHT = "SELECT course_id from user.course WHERE";
	public static String FETCH_REGISTERED_COURSES = "SELECT * from user.registered_courses";
	public static String CREATE_QUERY_GRADE = "INSERT INTO user.report_card (report_id,student_id,course_id,grade,semester,year) VALUES (?,?,?,?,?,?)";
}
