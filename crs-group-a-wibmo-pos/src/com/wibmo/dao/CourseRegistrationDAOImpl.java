package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.utils.DBUtils;

public class CourseRegistrationDAOImpl implements CourseRegistrationDAO {

	@Override
	public void save(CourseRegistration courseRegistration) {
		
		String sql = "INSERT INTO user.registered_courses("
				+ "student_id, "
				+ "semester,"
				+ "year,"
				+ "primary_course_1_id,"
				+ "primary_course_2_id,"
				+ "primary_course_3_id,"
				+ "primary_course_4_id,"
				+ "alternative_course_1_id,"
				+ "alternative_course_2_id,"
				+ "is_approved) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistration.getStudentId());
			stmt.setInt(2, courseRegistration.getSemester());
			stmt.setInt(3, courseRegistration.getYear());
			stmt.setInt(4, courseRegistration.getPrimaryCourse1Id());
			stmt.setInt(5, courseRegistration.getPrimaryCourse2Id());
			stmt.setInt(6, courseRegistration.getPrimaryCourse3Id());
			stmt.setInt(7, courseRegistration.getPrimaryCourse4Id());
			stmt.setInt(8, courseRegistration.getAlternativeCourse1Id());
			stmt.setInt(9, courseRegistration.getAlternativeCourse2Id());
			stmt.setString(10, courseRegistration.getRegistrationStatus().toString());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
	}

	@Override
	public CourseRegistration findByStudent(Student student) {
		
		CourseRegistration courseRegistration = null;
		
		String sql = "SELECT * FROM user.registered_courses "
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
						rs.getInt("reg_id"),
						rs.getInt("student_id"),
						rs.getInt("semester"),
						rs.getInt("year"),
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
	public RegistrationStatus findRegistrationStatusByStudent(Student student) {
		
		RegistrationStatus registrationStatus = null;
		
		String sql = "SELECT registration_status FROM user.registered_courses "
				+ "WHERE student_id = ? "
				+ "AND semester = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				registrationStatus = RegistrationStatus
						.valueOf(rs.getString("registration_status"));
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
				+ "FROM user.registered_courses "
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

	@Override
	public Set<Integer> findAllStudentIdsByCourseId(Integer courseId) {
		
		Set<Integer> studentIds = new HashSet<>();
		
		String sql = "SELECT student_id FROM user.registered_courses "
				+ "WHERE primary_course_1_id = ? "
				+ "OR primary_course_2_id = ? "
				+ "OR primary_course_3_id = ? "
				+ "OR primary_course_4_id = ? "
				+ "OR alternative_course_1_id = ? "
				+ "OR alternative_course_2_id = ?";
		
		Connection conn = DBUtils.getConnection();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			for(int i = 1; i <= 6; i++) {
				stmt.setInt(i, courseId);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				studentIds.add(rs.getInt("student_id"));
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return studentIds;
	}
	
//	public Map<Integer,ArrayList<Integer>> getStudentsByCourseId(Professor professor) {
//		String sql = "SELECT student_id "
//				+ "FROM registered_courses "
//				+ "WHERE"
//				+ "primary_course_1_id = ?,"
//				+ "OR"
//				+ "primary_course_2_id = ?,"
//				+ "OR"
//				+ "primary_course_3_id = ?,"
//				+ "OR"
//				+ "primary_course_4_id = ?,";
//		
//		Map<Integer,ArrayList<Integer>> studentsByCourseID = new HashMap<Integer,ArrayList<Integer>>();
//
//		Connection conn = DBUtils.getConnection();
//		ArrayList<Integer> courseIds = new ArrayList<Integer>();
//		professor.getCoursesTaught().forEach(course -> courseIds.add(course.getCourseId()));
//		try {
//			for(Integer courseId : courseIds){
//				PreparedStatement stmt = conn.prepareStatement(sql);
//				stmt.setInt(1, courseId);
//				stmt.setInt(2, courseId);
//				stmt.setInt(3, courseId);
//				stmt.setInt(4, courseId);
//				ResultSet rs = stmt.executeQuery(sql);
//				while(rs.next()){
//					if(studentsByCourseID.containsKey(courseId)){
//						studentsByCourseID.get(courseId).add(rs.getInt("student_id"));
//					}
//					else{
//						ArrayList<Integer> temp = new ArrayList<Integer>();
//						temp.add(rs.getInt("student_id"));
//						studentsByCourseID.put(courseId, temp);
//					}
//				}
//			}
//			
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
////			e.printStackTrace();
//		}
//		
//		return studentIds;
//	}

	@Override
	public CourseRegistration findByCourseIdAndSemesterAndYear(Integer courseId, Integer semester, Integer year) {

		CourseRegistration courseRegistration = null;
		
		String sql = "SELECT * FROM user.registered_courses "
				+ "WHERE semester = ? "
				+ "AND year = ? "
				+ "AND ("
				+ "primary_course_1_id = ? "
				+ "OR primary_course_2_id = ? "
				+ "OR primary_course_3_id = ? "
				+ "OR primary_course_4_id = ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, semester);
			stmt.setInt(2, year);
			for(int i = 3; i <= 6; i++) {
				stmt.setInt(i, courseId);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				courseRegistration = new CourseRegistration(
						rs.getInt("reg_id"),
						rs.getInt("student_id"),
						rs.getInt("semester"),
						rs.getInt("year"),
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
	public Map<Integer, ArrayList<Integer>> getStudentsByCourseId(Professor professor) {
		// TODO Auto-generated method stub
		return null;
	}

	public void viewCourseRegistrationStatus(RegistrationStatus regStatus){
		String sql = "SELECT * FROM user.registered_courses where regStatus=?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setObject(1, regStatus);

			ResultSet rs = stmt.executeQuery();
			String regId = "Registration Id", studentId = "Student Id", sem="Semester", year="year", pCourse1="Primary Course 1", pCourse2="Primary Course 2", pCourse3="Primary Course 3", pCourse4="Primary Course 4", aCourse1="Alternate Course 1", aCourse2="Alternate Course 2", regSt="reg_status";
		    System.out.format("%10s%16s%16s%16s%16s%16s%16s%16s%16s%16s%16s", regId, studentId, sem, year, pCourse1, pCourse2, pCourse3, pCourse4, aCourse1, aCourse2, regSt+ "\n");
		    while(rs.next()){
		         int regId1  = rs.getInt("reg_id");
				 int sid  = rs.getInt("student_id");
		         int sem1 = rs.getInt("semester");
				 int year1  = rs.getInt("year");
				 int prCourse1 = rs.getInt("primary_course_1_id");
				 int prCourse2 = rs.getInt("primary_course_2_id");
				 int prCourse3 = rs.getInt("primary_course_3_id");
				 int prCourse4 = rs.getInt("primary_course_4_id");
				 int alCourse1 = rs.getInt("alternate_course_1_id");
				 int alCourse2= rs.getInt("alternate_course_2_id");
		         String regSt1 = rs.getString("reg_status");
		    

		         System.out.format("%10s%16s%16s%16s%16s%16s%16s%16s%16s%16s%16s", regId1, sid, sem1, year1, prCourse1, prCourse2, prCourse3, prCourse4, alCourse1, alCourse2, regSt1+ "\n");
		     }

		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean approveRegistrationStatus(int courseRegId){
		String sql = "UPDATE user.registered_courses SET reg_status=? where reg_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "APPROVED");
			stmt.setInt(2, courseRegId);
			stmt.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean rejectRegistrationStatus(int courseRegId){
		String sql = "UPDATE user.registered_courses SET reg_status=? where reg_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "REJECTED");
			stmt.setInt(2, courseRegId);
			stmt.executeUpdate();
			return true;
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
