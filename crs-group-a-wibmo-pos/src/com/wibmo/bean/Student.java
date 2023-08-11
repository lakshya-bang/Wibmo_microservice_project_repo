package com.wibmo.bean;

public class Student {
	int studentID;
	String studentName;
	ReportCard studentReportCard;
	
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public ReportCard getStudentReportCard() {
		return studentReportCard;
	}
	public void setStudentReportCard(ReportCard studentReportCard) {
		this.studentReportCard = studentReportCard;
	}
}
