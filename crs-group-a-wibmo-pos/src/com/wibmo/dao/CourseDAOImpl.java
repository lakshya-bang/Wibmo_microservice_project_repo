package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.utils.DBUtils;

public class CourseDAOImpl implements CourseDAO {


	@Override
	public List<Course> findAllByCourseIdIn(Set<Integer> courseIds) {

		
		List<Course> courses = new ArrayList<>();
		
		Connection conn = DBUtils.getConnection();
		try {
			for(Integer courseId : courseIds){
			String sql = "SELECT * FROM user.course "
				+ "WHERE course_id = " + courseId;
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Course temp = new Course(rs.getInt("course_id"), rs.getInt("year"), rs.getInt("semester"),rs.getString("name"), rs.getString("department"), rs.getInt("professor_id"), rs.getBoolean("isCancelled"), rs.getInt("no_of_seats"));
				courses.add(temp);
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
}

	@Override
	public List<Course> findAllBySemester(Integer semester) {
		String sql = "SELECT * FROM user.course "
				+ "WHERE semester = " + semester;
		List<Course> courses = new ArrayList<>();
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Course temp = new Course(rs.getInt("course_id"), rs.getInt("year"), rs.getInt("semester"),rs.getString("name"), rs.getString("department"), rs.getInt("professor_id"), rs.getBoolean("isCancelled"), rs.getInt("no_of_seats"));
				courses.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	@Override
	public List<Course> findCourseByProfessorID(Integer professorID) {
		String sql = "SELECT * FROM user.course "
				+ "WHERE professor_id = " + professorID;
		List<Course> courses = new ArrayList<>();
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Course temp = new Course(rs.getInt("course_id"), rs.getInt("year"), rs.getInt("semester"),rs.getString("name"), rs.getString("department"), rs.getInt("professor_id"), rs.getBoolean("isCancelled"), rs.getInt("no_of_seats"));
				courses.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}


}
