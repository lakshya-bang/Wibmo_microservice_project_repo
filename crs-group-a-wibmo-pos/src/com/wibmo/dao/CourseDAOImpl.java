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
		
		String sql = SQLConstants.FETCH_COURSE_BY_SEMESTER + semester;
		
		List<Course> courses = new ArrayList<>();
		
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
						rs.getInt("is_Cancelled") == 1, 
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
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return courses;
	}


	//moved adminDAO method to here
	@Override
	public void viewAllCourse() {
		String sql = "SELECT * FROM course";
		
		Connection conn = DBUtils.getConnection();
		try {
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		String id = "Course Id", title = "Course Title", sem="Semester", year="year", dept="Department", profId="Professor Id", isCancel="Is Cancelled", noOfSeats="No. of Seats", courseType="Course Type";
		    System.out.format("%10s%16s%16s%16s%16s%16s%16s%16s%16s", id, title, sem, year, dept, profId, isCancel, noOfSeats, courseType+ "\n");
		    while(rs.next()){
		         int cid  = rs.getInt("course_id");
		         String title1 = rs.getString("course_title");
		         int sem1 = rs.getInt("semester");
				 int year1  = rs.getInt("year");
		         String dept1 = rs.getString("department");
		         int profId1 = rs.getInt("professor_id");
				 int isCancel1  = rs.getInt("is_cancelled");
		         int noOfSeat1 = rs.getInt("no_of_seats");
		         String courseType1 = rs.getString("course_type");

		         System.out.format("%10s%16s%16s%16s%16s%16s%16s%16s%16s", cid, title1, sem1, year1, dept1, profId1, isCancel1, noOfSeat1, courseType1+ "\n");
		     }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean saveCourse(Course course) {
		String sql = "INSERT INTO course("
				+ "course_title, "
				+ "department,"
				+ "year,"
				+ "semester,"
				+ "course_type"
				+ "VALUES(?, ?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, course.getCourseTitle());
			stmt.setString(2, course.getDepartment());
			stmt.setInt(3, course.getYear());
			stmt.setInt(4, course.getSemester());
			
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean deleteCourse(int courseId) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "Delete from course where course_id = ?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, courseId);
			int result=stmt.executeUpdate();
			return (result==0?false:true);
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean assignCoursesToProfessor(int courseId, int professorId) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "UPDATE course SET professor_id=? where course_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, professorId);
			stmt.setInt(2, courseId);
			int result=stmt.executeUpdate();
			return (result==0?false:true);
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
	}

}
