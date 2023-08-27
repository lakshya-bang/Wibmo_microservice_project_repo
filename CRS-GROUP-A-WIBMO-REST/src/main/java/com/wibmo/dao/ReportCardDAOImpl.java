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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Repository;

import com.wibmo.constant.SQLConstants;
import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class ReportCardDAOImpl implements ReportCardDAO {
	
	private static final Logger logger = LogManager.getLogger(ReportCardDAOImpl.class);
	
	@Override
	public void save(ReportCard reportCard) {
		
		String sql = "INSERT INTO report_card("
				+ "student_id,"
				+ "course_id,"
				+ "grade,"
				+ "semester,"
				+ "year) "
				+ "VALUES(?, ?, ?, ?, ?)";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, reportCard.getStudentId());
			stmt.setInt(2, reportCard.getCourseId());
			stmt.setString(3, reportCard.getGrade());
			stmt.setInt(4, reportCard.getSemester());
			stmt.setInt(5, reportCard.getYear());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	
	}

	// TODO: Rename to findAllByStudentIdGroupedBySemester
	@Override
	public Map<Integer, ArrayList<ReportCard>> findAllByStudentId(Integer studentId) {
		
		Map<Integer,ArrayList<ReportCard>> semesterToReportCardsMap = new HashMap<Integer,ArrayList<ReportCard>>();
		
		String sql = SQLConstants.FETCH_REPORT_CARD_BY_STUDENT_ID;
		
		Connection conn = DBUtils.getConnection();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,studentId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				ReportCard reportCard = new ReportCard(
						rs.getInt("report_id"),
						rs.getInt("student_id"),
						rs.getInt("course_id"),
						rs.getString("grade"),
						rs.getInt("semester"),
						rs.getInt("year"));
				
				Integer semester = reportCard.getSemester();
				
				if(!semesterToReportCardsMap.containsKey(semester)) {
					semesterToReportCardsMap.put(semester, new ArrayList<>());
				}
				semesterToReportCardsMap.get(semester).add(reportCard);
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return semesterToReportCardsMap;	
	}

	@Override
	public void update(ReportCard reportCard) {
		
		String sql = "UPDATE report_card "
				+ "SET student_id = ?, "
				+ "course_id = ?, "
				+ "grade = ?, "
				+ "semester = ?, "
				+ "year = ? "
				+ "WHERE report_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, reportCard.getStudentId());
			stmt.setInt(2, reportCard.getCourseId());
			stmt.setString(3, reportCard.getGrade());
			stmt.setInt(4, reportCard.getSemester());
			stmt.setInt(5, reportCard.getYear());
			stmt.setInt(6, reportCard.getReportId());
			
			stmt.executeUpdate();
		}
		catch(SQLException e){
			logger.error(e.getMessage());
		}
	}

	@Override
	public ReportCard findByStudentAndCourseId(Student student, Integer courseId) {
		
		ReportCard reportCard = null;
		
		String sql = "SELECT * FROM report_card "
				+ "WHERE student_id = ? "
				+ "AND semester = ? "
				+ "AND course_id = ? ";
		
		Connection conn = DBUtils.getConnection();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, student.getStudentId());
			stmt.setInt(2, student.getCurrentSemester());
			stmt.setInt(3, courseId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				reportCard = new ReportCard(
						rs.getInt("report_id"),
						rs.getInt("student_id"),
						rs.getInt("course_id"),
						rs.getString("grade"),
						rs.getInt("semester"),
						rs.getInt("year"));
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return reportCard;
	}

}
