/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.wibmo.constant.SQLConstants;
import com.wibmo.entity.Student;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class StudentDAOImpl implements StudentDAO {
	private static Logger logger = Logger.getLogger(StudentDAOImpl.class);
	@Override
	public Student findById(Integer studentId) {
		
		Student student = null;
		
		String sql = SQLConstants.FETCH_STUDENT_BY_ID;
		
		Connection conn = DBUtils.getConnection();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, studentId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				student = new Student(
						rs.getInt("student_id"),
						rs.getString("student_email"),
						rs.getString("student_name"),
						rs.getInt("semester"));
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
//			e.printStackTrace();
		}
		
		return student;
		
	}

	@Override
	public void save(Student student) {
		
		String sql = "INSERT INTO student VALUES(?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setString(2, student.getStudentName());
			stmt.setInt(3, student.getCurrentSemester());
			stmt.setString(4, student.getStudentEmail());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
//			e.printStackTrace();
		}
	}
}
