package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.utils.DBUtils;

public class CourseRegistrationDAOImpl implements CourseRegistrationDAO {

	@Override
	public void save(CourseRegistration courseRegistration) {
		
		String sql = "INSERT INTO registered_courses("
				+ "student_id, "
				+ "semester,"
				+ "primary_course_1_id,"
				+ "primary_course_2_id,"
				+ "primary_course_3_id,"
				+ "primary_course_4_id,"
				+ "alternative_course_1_id,"
				+ "alternative_course_2_id,"
				+ "is_approved) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistration.getStudentId());
			stmt.setInt(2, courseRegistration.getSemester());
			stmt.setInt(3, courseRegistration.getPrimaryCourse1Id());
			stmt.setInt(4, courseRegistration.getPrimaryCourse2Id());
			stmt.setInt(5, courseRegistration.getPrimaryCourse3Id());
			stmt.setInt(6, courseRegistration.getPrimaryCourse4Id());
			stmt.setInt(7, courseRegistration.getAlternativeCourse1Id());
			stmt.setInt(8, courseRegistration.getAlternativeCourse2Id());
			stmt.setString(9, courseRegistration.getRegistrationStatus().toString());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
	}

	@Override
	public CourseRegistration findByStudent(Student student) {
		
		CourseRegistration courseRegistration = null;
		
		String sql = "SELECT * FROM registered_courses "
				+ "WHERE student_id = ? "
				+ "AND semester = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				courseRegistration = new CourseRegistration(
						rs.getInt("course_reg_id"),
						rs.getInt("student_id"),
						rs.getInt("semester"),
						rs.getInt("primary_course_1_id"),
						rs.getInt("primary_course_2_id"),
						rs.getInt("primary_course_3_id"),
						rs.getInt("primary_course_4_id"),
						rs.getInt("alternative_course_1_id"),
						rs.getInt("alternative_course_2_id"),
						RegistrationStatus.valueOf(
								rs.getString("registration_status")));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return courseRegistration;
	}

	@Override
	public String findRegistrationStatusByStudent(Student student) {
		
		String registrationStatus = null;
		
		String sql = "SELECT registration_status FROM registered_courses "
				+ "WHERE student_id = ? "
				+ "AND semester = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				registrationStatus = rs.getString("registration_status");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return registrationStatus;
	}
	
	@Override
	public Boolean existsByStudent(Student student) {
		
		int row = 0;
		
		String sql = "SELECT COUNT(*) AS count "
				+ "FROM registered_courses "
				+ "WHERE student_id = ?"
				+ "AND semester = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			
			row = stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return row == 1;
	}

}
