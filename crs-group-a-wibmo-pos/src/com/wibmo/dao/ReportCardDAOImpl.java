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

import com.wibmo.bean.ReportCard;
import com.wibmo.constant.SQLConstants;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class ReportCardDAOImpl implements ReportCardDAO {

	private static volatile ReportCardDAOImpl instance = null;
	
	private ReportCardDAOImpl() {
		
	}
	
	public static ReportCardDAOImpl getInstance() {
        if (instance == null) {
            synchronized (ReportCardDAOImpl.class) { //It's a synchronized object that will thread safe.
                instance = new ReportCardDAOImpl();
            }
        }
        return instance;
    }
	
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
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
	
	}

	@Override
	public Map<Integer, ArrayList<ReportCard>> findAllByStudentId(Integer studentId) {
		String sql = SQLConstants.FETCH_REPORT_CARD_BY_STUDENT_ID;
		Connection conn = DBUtils.getConnection();
		Map<Integer,ArrayList<ReportCard>> semesterToReportCardsMap = new HashMap<Integer,ArrayList<ReportCard>>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,studentId);
			ResultSet rs = stmt.executeQuery();
			ReportCard tempReportCard = new ReportCard(
					rs.getInt("report_id"),
					rs.getInt("student_id"),
					rs.getInt("course_id"),
					rs.getString("grade"),
					rs.getInt("semester"),
					rs.getInt("year"));
			if(semesterToReportCardsMap
					.containsKey(rs.getInt("semester"))){	
				semesterToReportCardsMap
					.get(rs.getInt("semester"))
					.add(tempReportCard);
			} else {
				ArrayList<ReportCard> tempReportCards = new ArrayList<ReportCard>();
				tempReportCards.add(tempReportCard);
				semesterToReportCardsMap.put(
						rs.getInt("semester"), 
						tempReportCards);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return semesterToReportCardsMap;	
	}

	// TODO: rename to checkReportCardDetails
	@Override
	public boolean checkGradeDetails(ReportCard reportCard) {
		String sql = SQLConstants.FETCH_REPORT_CARD_BY_REPORT_ID + reportCard.getReportId();
		Connection conn = com.wibmo.utils.DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			// TODO: Should use .equals() method instead.
			if(rs.next() 
					&& (rs.getInt("report_id") == reportCard.getReportId() 
						&& rs.getInt("student_id") == reportCard.getStudentId() 
						&& rs.getInt("course_id") == reportCard.getCourseId() 
						&& rs.getString("grade").equals(reportCard.getGrade()) 
						&& rs.getInt("semester") == reportCard.getSemester() 
						&& rs.getInt("year") == reportCard.getYear())){
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void updateByGradeId(ReportCard reportCard) {
		String sql = SQLConstants.UPDATE_REPORT_CARD_BY_REPORT_ID;
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
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
	}

}
