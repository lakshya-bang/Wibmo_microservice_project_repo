/**
 * 
 */
package com.wibmo.bean;

import java.util.HashMap;

/**
 * 
 */
public class ReportCard {
	int reportID;
	HashMap<Integer,Grade> grades= new HashMap<Integer,Grade>();
	/**
	 * @return the reportID
	 */
	public int getReportID() {
		return reportID;
	}
	/**
	 * @param reportID the reportID to set
	 */
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	/**
	 * @return the grades
	 */
	public HashMap<Integer, Grade> getGrades() {
		return grades;
	}
	/**
	 * @param grades the grades to set
	 */
	public void setGrades(HashMap<Integer, Grade> grades) {
		this.grades = grades;
	}
	
	
	
}
