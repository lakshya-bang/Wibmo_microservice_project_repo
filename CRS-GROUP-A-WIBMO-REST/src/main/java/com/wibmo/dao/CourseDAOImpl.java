package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Repository;

import com.wibmo.enums.CourseType;
import com.wibmo.constant.SQLConstants;
import com.wibmo.entity.Course;
import com.wibmo.utils.DBUtils;

@Repository
public class CourseDAOImpl implements CourseDAO {
	
	private static final Logger logger = LogManager.getLogger(CourseDAOImpl.class);
	
	@Override
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		}
		
		return courses;
	}

	@Override
	public List<Course> findAllByProfessorId(Integer professorId) {
		
		List<Course> courses = new ArrayList<>();
		
		String sql = "SELECT * FROM user.course "
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
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		}
	
		return courseType;
	}

	@Override
	public Boolean existsByCourseId(Integer courseId) {
		
		String sql = "SELECT course_id FROM course "
				+ "WHERE course_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, courseId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return Boolean.TRUE;
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return Boolean.FALSE;
	}

	@Override
	public List<Course> findAll() {
		
		List<Course> courses = new ArrayList<>();
		
		String sql = "SELECT * FROM course";
		
		Connection conn = DBUtils.getConnection();
		
		try {
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
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return courses;
	}

	@Override
	public Boolean save(Course course) {
		
		String sql = "INSERT INTO course("
				+ "course_title, "
				+ "department,"
				+ "year,"
				+ "semester,"
				+ "course_type) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			// stmt.setInt(1, course.getCourseId());
			stmt.setString(1, course.getCourseTitle());
			stmt.setString(2, course.getDepartment());
			stmt.setInt(3, course.getYear());
			stmt.setInt(4, course.getSemester());
			stmt.setString(5, course.getCourseType().toString());
			
			stmt.executeUpdate();

			return Boolean.TRUE;

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		return Boolean.FALSE;
	}


	@Override
	public Boolean deleteCourseById(Integer courseId) {
		
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		
		try {
			String sql = "DELETE FROM course WHERE course_id = ?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, courseId);
			
			int row = stmt.executeUpdate();
			
			if(row == 1) {
				return Boolean.TRUE;
			}
			
		} catch(SQLException e) {
			logger.error(e.getMessage());
		}
		
		return Boolean.FALSE;
	}

	@Override
	public Boolean setProfessorIdAsWhereCourseIdIs(Integer professorId, Integer courseId) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "UPDATE course SET professor_id=? WHERE course_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, professorId);
			stmt.setInt(2, courseId);
			int row=stmt.executeUpdate();
			if(row == 1) {
				return Boolean.TRUE;
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
		}
		return Boolean.FALSE;
	}

	@Override
	public Integer findProfessorIdByCourseId(Integer courseId) {

		Integer professorId = null;
		
		String sql = "SELECT professor_id FROM course "
				+ "WHERE course_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				professorId = (Integer) rs.getObject("professor_id");
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return professorId;
	}

}
