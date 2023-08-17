package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.enums.CourseType;
import com.wibmo.constant.SQLConstants;
import com.wibmo.utils.DBUtils;

public class CourseDAOImpl implements CourseDAO {
	
	public List<Course> findAllByCourseIdIn(Set<Integer> courseIds) {

		List<Course> courses = new ArrayList<>();
		
		Connection conn = DBUtils.getConnection();
		try {
			for(Integer courseId : courseIds){
				String sql = SQLConstants.FETCH_COURSE_BY_ID + courseId;
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					courses.add(new Course(
							rs.getInt("course_id"), 
							rs.getString("course_title"),
							rs.getInt("semester"),
							rs.getInt("year"), 
							rs.getString("department"), 
							rs.getInt("professor_id"), 
							rs.getInt("is_cancelled") == 1, 
							rs.getInt("no_of_seats"),
							CourseType.valueOf(rs.getString("course_type"))));
				}
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return courses;
	}

	@Override
	public List<Course> findAllBySemester(Integer semester) {
		
		String sql = SQLConstants.FETCH_COURSE_BY_SEMESTER;
		
		List<Course> courses = new ArrayList<>();
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, semester);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				courses.add(new Course(
						rs.getInt("course_id"),
						rs.getString("course_title"),
						rs.getInt("semester"),
						rs.getInt("year"),
						rs.getString("department"), 
						rs.getInt("professor_id"), 
						rs.getInt("is_cancelled") == 1, 
						rs.getInt("no_of_seats"),
						CourseType.valueOf(rs.getString("course_type"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}

	@Override
	public List<Course> findAllByProfessorId(Integer professorId) {
		
		List<Course> courses = new ArrayList<>();
		
		String sql = "SELECT * FROM course "
				+ "WHERE professor_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, professorId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				courses.add(new Course(
						rs.getInt("course_id"),
						rs.getString("course_title"),
						rs.getInt("semester"),
						rs.getInt("year"),
						rs.getString("department"),
						rs.getInt("professor_id"),
						rs.getInt("is_cancelled") == 1,
						rs.getInt("no_of_seats"),
						CourseType.valueOf(rs.getString("course_type"))));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return courses;
	}

	@Override
	public CourseType findCourseTypeByCourseId(Integer courseId) {
		
		CourseType courseType = null;
		
		String sql = "SELECT course_type FROM course "
				+ "WHERE course_id = ?";
		
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				courseType = CourseType.valueOf(rs.getString("course_type"));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//				e.printStackTrace();
		}
	
		return courseType;
	}
}
