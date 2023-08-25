package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.utils.DBUtils;

@Repository
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
				+ "reg_status) "
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
								rs.getString("reg_status")));
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
		
		String sql = "SELECT reg_status FROM registered_courses "
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
						.valueOf(rs.getString("reg_status"));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return registrationStatus;
	}
	
	@Override
	public Boolean existsByStudent(Student student) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(*) AS count "
				+ "FROM registered_courses "
				+ "WHERE student_id = ? "
				+ "AND semester = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return count == 1;
	}
	
	@Override
	public Boolean existsByStudentAndCourseId(Student student, Integer courseId) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(*) AS count "
				+ "FROM registered_courses "
				+ "WHERE student_id = ? "
				+ "AND semester = ? "
				+ "AND ("
				+ "primary_course_1_id = ? "
				+ "OR primary_course_2_id = ? "
				+ "OR primary_course_3_id = ? "
				+ "OR primary_course_4_id = ? "
				+ "OR alternative_course_1_id = ? "
				+ "OR alternative_course_2_id = ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			
			for(int i = 3; i <= 8; i++) {
				stmt.setInt(i, courseId);
			}
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return count == 1;
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
	public Integer findCourseRegistrationIdByStudent(Student student) {
		
		Integer courseRegistrationId = -1;
		
		String sql = "SELECT reg_id FROM registered_courses "
				+ "WHERE student_id = ? "
				+ "AND semester = ?";
		
		Connection conn = DBUtils.getConnection();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				courseRegistrationId = rs.getInt("reg_id");
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return courseRegistrationId;
	}

	@Override
	public Integer findAlternativeCourseIdIndexByCourseRegistrationIdForCourse(
			Integer courseRegistrationId, Integer courseId) {
		
		String sql = "SELECT alternative_course_1_id "
				+ "FROM registered_courses "
				+ "WHERE reg_id = ?";
		
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistrationId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(courseId == rs.getInt("alternative_course_1_id")) {
					return 1;
				} else {
					return 2;
				}
			}
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return -1;
	}


	@Override
	public Integer findPrimaryCourseIdIndexByCourseRegistrationIdForCourse(
			Integer courseRegistrationId,
			Integer courseId) {
		
		String sql = "SELECT primary_course_1_id, "
				+ "primary_course_2_id,"
				+ "primary_course_3_id "
				+ "FROM registered_courses "
				+ "WHERE reg_id = ?";
		
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistrationId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(courseId == rs.getInt("primary_course_1_id")) {
					return 1;
				}
				if(courseId == rs.getInt("primary_course_2_id")) {
					return 2;
				}
				if(courseId == rs.getInt("primary_course_3_id")) {
					return 3;
				}
				return 4;
			}
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return -1;
		
	}

	@Override
	public void setAlternativeCourseIdAsNullAtIndexByCourseRegistrationId(
			Integer index, Integer courseRegistrationId) {
		
		String sql = "UPDATE registered_courses "
				+ "SET alternative_course_" + index + "_id = NULL "
				+ "WHERE reg_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistrationId);

			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
	}

	@Override
	public void setPrimaryCourseIdAsNullAtIndexByCourseRegistrationId(
			Integer index, Integer courseRegistrationId) {
		
		String sql = "UPDATE registered_courses "
				+ "SET primary_course_" + index + "_id = NULL "
				+ "WHERE reg_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistrationId);

			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	@Override
	public Map<Integer, ArrayList<Integer>> getStudentsByCourseId(Professor professor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer findFirstVacantAlternativeCourseIdIndexByCourseRegistrationId(
			Integer courseRegistrationId) {
		
		String sql = "SELECT alternative_course_1_id, "
				+ "alternative_course_2_id "
				+ "FROM registered_courses "
				+ "WHERE reg_id = ?";

		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistrationId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				// expecting a null (SQL NULL)
				if(null == rs.getObject("alternative_course_1_id")) {
					return 1;
				}
				if(null == rs.getObject("alternative_course_2_id")) {
					return 2;
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return -1;
	}	

	@Override
	public Integer findFirstVacantPrimaryCourseIdIndexByCourseRegistrationId(
			Integer courseRegistrationId) {
		
		String sql;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt;
		ResultSet rs;
		
		try {
			sql = "SELECT primary_course_1_id, "
					+ "primary_course_2_id, "
					+ "primary_course_3_id, "
					+ "primary_course_4_id "
					+ "FROM registered_courses "
					+ "WHERE reg_id = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseRegistrationId);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				// expecting a null (SQL NULL)
				if(null == rs.getObject("primary_course_1_id")) {
					return 1;
				}
				if(null == rs.getObject("primary_course_2_id")) {
					return 2;
				}
				if(null == rs.getObject("primary_course_3_id")) {
					return 3;
				}
				if(null == rs.getObject("primary_course_4_id")) {
					return 4;
				}
			}
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public void setAlternativeCourseIdAsAtIndexByCourseRegistrationId(
			Integer courseId,
			Integer alternativeCourseIdIndex, 
			Integer courseRegistrationId) {
		
		String sql = "UPDATE registered_courses "
				+ "SET alternative_course_" + alternativeCourseIdIndex + "_id = ? " 
				+ "WHERE reg_id = ?";

		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseId);
			stmt.setInt(2, courseRegistrationId);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	}

	@Override
	public void setPrimaryCourseIdAsAtIndexByCourseRegistrationId(
			Integer courseId, 
			Integer primaryCourseIdIndex,
			Integer courseRegistrationId) {
		
		String sql = "UPDATE registered_courses "
				+ "SET primary_course_" + primaryCourseIdIndex + "_id = ? " 
				+ "WHERE reg_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseId);
			stmt.setInt(2, courseRegistrationId);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
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

	@Override
	public List<CourseRegistration> findAllByRegistrationStatus(
			RegistrationStatus registrationStatus) {
		
		List<CourseRegistration> courseRegistrations = new ArrayList<>();
		
		String sql = "SELECT * FROM user.registered_courses "
				+ "WHERE reg_status = ?";
		
		Connection conn = DBUtils.getConnection();
		
		try {
		
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, registrationStatus.toString());
	
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				courseRegistrations.add(new CourseRegistration(
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
						RegistrationStatus.valueOf(rs.getString("reg_status"))));
			}

		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return courseRegistrations;
	}

	// TODO: Improve PreparedStatement
	@Override
	public Boolean updateRegistrationStatusAsByIdIn(
			RegistrationStatus registrationStatus,
			Set<Integer> courseRegistrationIds) {
		
		StringBuilder sql = new StringBuilder("UPDATE registered_courses "
				+ "SET reg_status = ? "
				+ "WHERE reg_id IN (");
		courseRegistrationIds.forEach(courseRegistrationId -> sql.append(courseRegistrationId).append(","));
		sql.replace(sql.length() - 1, sql.length(), ")");
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, registrationStatus.toString());
			
			int rows = stmt.executeUpdate();
			
			if(courseRegistrationIds.size() == rows) {
				return Boolean.TRUE;
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return Boolean.FALSE;
	}
//	
//	@Override
//	public boolean 
}
