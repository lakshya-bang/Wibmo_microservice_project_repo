/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wibmo.bean.Professor;
import com.wibmo.constants.SQLConstants;

/**
 * 
 */
public class ProfessorDAOImpl implements ProfessorDAO{
	
	private static volatile ProfessorDAOImpl instance = null;
	private ProfessorDAOImpl() {
		
	}
	
	public static ProfessorDAOImpl getInstance() {
        if (instance == null) {
            synchronized (ProfessorDAOImpl.class) { //It's a synchronized object that will thread safe.
                instance = new ProfessorDAOImpl();
            }
        }
        return instance;
    }
	
	@Override
	public Map<Integer,ArrayList<Integer>> getStudentList(List<Integer> courses) {
		Map<Integer,ArrayList<Integer>> studentMap = new HashMap<Integer,ArrayList<Integer>>();
		PreparedStatement stmt = null;
		Connection conn = com.wibmo.utils.DBUtils.getConnection();
		Map<Integer,String> mapStudentCourse = new HashMap<Integer,String>();
		try {
			stmt = conn.prepareStatement(SQLConstants.FETCH_REGISTERED_COURSES);
		    ResultSet rs = stmt.executeQuery(SQLConstants.FETCH_REGISTERED_COURSES);
		   while(rs.next()) {
			   mapStudentCourse.put(rs.getInt(1),rs.getString(2));
		   }
		   }catch(SQLException se) {
		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }
		for(Integer id : courses) {
			for(Map.Entry<Integer, String> entry : mapStudentCourse.entrySet()) {
				if(entry.getValue().contains(Integer.toString(id))) {
					if(studentMap.containsKey(id)) {
						studentMap.get(id).add(entry.getKey());
					}
					else {
						studentMap.put(id, new ArrayList<Integer>(Arrays.asList(entry.getKey())));
					}
				}
			}
		}
		return studentMap;
		}
		

	@Override
	public List<Integer> fetchCoursesTaught(long id, int year, int semester) {
		List<Integer> courseIds = new ArrayList<>();
		PreparedStatement stmt = null;
		Connection conn = com.wibmo.utils.DBUtils.getConnection();
		try {
		stmt = conn.prepareStatement(SQLConstants.PROFESSOR_COURSE_TAUGHT + " semester = " + semester + " AND year =" + year + " AND professor_id= "+ id);
	    ResultSet rs = stmt.executeQuery(SQLConstants.PROFESSOR_COURSE_TAUGHT + " semester = " + semester + " AND year =" + year + " AND professor_id= "+ id);
	   while(rs.next()) {
		   courseIds.add(rs.getInt("course_id"));
	   }
	   return courseIds;
	   }catch(SQLException se) {
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }
		return null;
	}

	@Override
	public Boolean updateGrades(Map<Integer, Character> gradeSheet, Integer year, Integer semester, Integer reportId, Integer studentId) {
		PreparedStatement stmt = null;
		Connection conn = com.wibmo.utils.DBUtils.getConnection();
		
		try {
		for(Map.Entry<Integer, Character> entry : gradeSheet.entrySet()) {
		
			stmt = conn.prepareStatement(SQLConstants.CREATE_QUERY_GRADE);
			stmt.setInt(1, reportId);  // This would set age
		    stmt.setInt(2,studentId);
		    stmt.setInt(3, entry.getKey());
		    stmt.setString(4, String.valueOf(entry.getValue()));
		    stmt.setInt(5, semester);
		    stmt.setInt(6, year);
		    stmt.executeUpdate();   
		}
		stmt.close();
	    conn.close();
		}catch(SQLException se){
		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }
		System.out.println("Successfully added the grade details......");
		return true;
	}
	
	

}
