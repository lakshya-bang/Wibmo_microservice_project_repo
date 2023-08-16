/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.wibmo.bean.Grade;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class GradeDAOImpl implements GradeDAO {

	private static volatile GradeDAOImpl instance = null;
	
	private GradeDAOImpl() {
		
	}
	
	public static GradeDAOImpl getInstance() {
        if (instance == null) {
            synchronized (GradeDAOImpl.class) { //It's a synchronized object that will thread safe.
                instance = new GradeDAOImpl();
            }
        }
        return instance;
    }
	
	@Override
	public void save(Grade grade) {
		
		String sql = "INSERT INTO grade_details("
				+ "student_id,"
				+ "course_id,"
				+ "grade,"
				+ "semester,"
				+ "year) "
				+ "VALUES(?, ?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, grade.getStudentId());
			stmt.setInt(2, grade.getCourseId());
			stmt.setString(3, grade.getGrade());
			stmt.setInt(4, grade.getSemester());
			stmt.setInt(5, grade.getYear());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	
	}

	@Override
	public Map<Integer, ArrayList<Grade>> findAllByStudentId(Integer studentId) {
		String sql = "SELECT * from report_card"
						+"WHERE student_id =?";
		Connection conn = DBUtils.getConnection();
		Map<Integer,ArrayList<Grade>> semesterToGrade = new HashMap<Integer,ArrayList<Grade>>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,studentId);
			ResultSet rs = stmt.executeQuery(sql);
			Grade tempGrade = new Grade(rs.getInt("grade_id"),rs.getInt("student_id"),rs.getInt("course_id"),rs.getString("grade"),rs.getInt("semester"),rs.getInt("year"));
			if(semesterToGrade.containsKey(rs.getInt("semester"))){	
				semesterToGrade.get(rs.getInt("semester")).add(tempGrade);
			}
			else{
				ArrayList<Grade> tempList = new ArrayList<Grade>();
				tempList.add(tempGrade);
				semesterToGrade.put(rs.getInt("semester"), tempList);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return semesterToGrade;	
	}

	@Override
	public boolean checkGradeDetails(Grade grade) {
		String sql = "SELECT * FROM user.report_card WHERE grade_id=" + grade.getGradeId();
		Connection conn = com.wibmo.utils.DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next() && (rs.getInt("grade_id") == grade.getGradeId() && rs.getInt("student_id") == grade.getStudentId() && rs.getInt("course_id") == grade.getCourseId() && rs.getString("grade").equals(grade.getGrade()) && rs.getInt("semester") == grade.getSemester() && rs.getInt("year") == grade.getYear())){
				return true;
			}
			else{
				return false;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updateByGradeId(Grade grade) {
		String sql = "UPDATE user.report_card SET student_id = ? , course_id = ?, grade = ?, semester = ?, year = ? WHERE grade_id = ?";
		Connection conn = DBUtils.getConnection();
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, grade.getGradeId());
			stmt.setInt(2, grade.getCourseId());
			stmt.setString(3, grade.getGrade());
			stmt.setInt(4, grade.getSemester());
			stmt.setInt(5, grade.getYear());
			stmt.executeUpdate();
		}
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}

}
