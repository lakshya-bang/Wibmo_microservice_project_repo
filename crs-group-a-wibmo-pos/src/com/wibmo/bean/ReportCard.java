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
	
	public int getReportID() {
		return reportID;
	}
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	public HashMap<Integer, Grade> getGrades() {
		return grades;
	}
	public void setGrades(HashMap<Integer, Grade> grades) {
		this.grades = grades;
	} 
	
}
