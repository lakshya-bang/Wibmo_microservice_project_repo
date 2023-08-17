package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.wibmo.bean.Admin;
import com.wibmo.bean.Course;
import com.wibmo.constant.SQLConstants;
import com.wibmo.utils.DBUtils;

public class AdminDAOImpl implements AdminDAO {

	@Override
	public boolean deleteCourse(String courseName) {
		// TODO Auto-generated method stub
				Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT course_id from course where course_title = ?";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, courseName);
			
			ResultSet resultSet=stmt.executeQuery(); 
			
			sql = "Delete from course where course_id = ?";
			stmt = conn.prepareStatement(sql);
			resultSet.next();
			int id=resultSet.getInt("course_id");
			stmt.setInt(1, id);
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
	public boolean saveAdmin(String email, String password,String name) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO auth_creds("
				+ "email, "
				+ "password,"
				+ "user_type,"
				+ "VALUES(?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			stmt.setString(3, "admin");
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		String sql1 = "SELECT user_id FROM auth_creds where user_email = ?";
		String sql2 = "UPDATE admin SET admin_name=? where admin_id = ?";

		 ResultSet resultSet;
		 int user_id=0;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql1);
			stmt.setString(1, email);			
			resultSet=stmt.executeQuery();
			resultSet.next();
			user_id=resultSet.getInt("user_id");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql2);
			stmt.setString(1, name);
			stmt.setInt(1, user_id);			
			stmt.executeUpdate(sql2);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
//			e.printStackTrace();
		}

		// throw new UnsupportedOperationException("Unimplemented method 'save'");
	}
	@Override
	public boolean saveCourse(Course course) {
		// TODO Auto-generated method stub
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
			stmt.setString(1, course.getName());
			stmt.setString(2, course.getDepartment());
			stmt.setInt(3, course.getYear());
			stmt.setInt(4, course.getSemester());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}


	@Override
	public boolean assignCoursesToProfessor(String courseName, String professorName) {
		// TODO Auto-generated method stub
				Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT user_id from user_details where name = ? and type ='professor'";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, professorName);
			
			ResultSet resultSet=stmt.executeQuery(); 
			String sql1 = "SELECT course_id from course where name = ?";
			stmt = conn.prepareStatement(sql1);

			stmt.setString(1, courseName);
			
			ResultSet resultSet1=stmt.executeQuery(); 
			resultSet.next();
			resultSet1.next();
			int professor_id =resultSet.getInt("professor_id"), course_id=resultSet1.getInt("course_id");


			sql = "UPDATE course SET professor_id=? where course_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, professor_id);
			stmt.setInt(2, course_id);
			int result=stmt.executeUpdate();
			return (result==0?false:true);
			
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}

		// throw new UnsupportedOperationException("Unimplemented method 'assignCoursesToProfessor'");
	}

	// public boolean approveStudentRegistration(int regId) throws SQLException {
	// 	Connection conn = DBUtils.getConnection();
	// 	PreparedStatement stmt = null;
    
	// 	try {
    //     	String sql = "UPDATE registered_courses SET reg_status = 'APPROVED' WHERE reg_id = ?";
	// 		stmt = conn.prepareStatement(sql);

	// 		stmt.setInt(1, regId);
	// 		int result=stmt.executeUpdate();
	// 		return (result==0?false:true);
	// 		} catch(SQLException e) {
	// 			e.printStackTrace();
	// 			return false;
	// 		} catch(Exception e) {
	// 			e.printStackTrace();
	// 			return false;
	// 		}
    // }

    public void generateReportCard(int studentId, int courseId, String grade, int semester, int year) throws SQLException {
        Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
    
		try {
			String sql = "INSERT INTO report_card (student_id, course_id, grade, semester, year) VALUES (?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.setString(3, grade);
            stmt.setInt(4, semester);
            stmt.setInt(5, year);

            stmt.executeUpdate();
        }  catch(SQLException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
    }
	
}
