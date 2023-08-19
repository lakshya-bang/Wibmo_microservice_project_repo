/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wibmo.bean.Student;
import com.wibmo.constant.SQLConstants;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class StudentDAOImpl implements StudentDAO {

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
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return student;
		
	}

}
