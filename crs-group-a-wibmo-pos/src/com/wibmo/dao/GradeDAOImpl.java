/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wibmo.bean.Grade;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class GradeDAOImpl implements GradeDAO {
	
	@Override
	public void save(Grade grade) {
		
		String sql = "INSERT INTO grade_details("
				+ "student_id,"
				+ "course_id,"
				+ "grade,"
				+ "semester,"
				+ "year) "
				+ "VALUES(?, ?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, grade.getStudentId());
			stmt.setInt(2, grade.getCourseId());
			stmt.setString(3, grade.getGrade());
			stmt.setInt(4, grade.getSemester());
			stmt.setInt(5, grade.getYear());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	
	}

}
