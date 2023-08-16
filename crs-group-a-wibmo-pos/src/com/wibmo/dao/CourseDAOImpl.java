package com.wibmo.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.CourseCatalogue;
import com.wibmo.bean.Professor;
import com.wibmo.utils.DBUtils;

public class CourseDAOImpl implements CourseDAO {

	@Override
	public List<CourseCatalogue> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseCatalogue> findAllByCourseIdIn(Set<Long> courseIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> findAllByCourseIdIn(Set<Integer> courseIds) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAllByCourseIdIn'");
	}

	@Override
	public List<Course> findAllBySemester(Integer semester) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAllBySemester'");
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
