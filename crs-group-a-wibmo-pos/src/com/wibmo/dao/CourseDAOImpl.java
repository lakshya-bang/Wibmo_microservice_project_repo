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
import com.wibmo.utils.DBUtils;

public class CourseDAOImpl implements CourseDAO {

	@Override
	public List<Course> findAllBySemester(Integer semester) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> findAllByCourseIdIn(Set<Integer> courseIds) {
		// TODO Auto-generated method stub
		return null;
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

}
