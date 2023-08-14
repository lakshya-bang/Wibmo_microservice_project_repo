package com.wibmo.bean;

public class Student {

	String studentName;
	ReportCard studentReportCard;
	
	private Long id;
	private Integer currentSemester;

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * @return the studentReportCard
	 */
	public ReportCard getStudentReportCard() {
		return studentReportCard;
	}
	/**
	 * @param studentReportCard the studentReportCard to set
	 */
	public void setStudentReportCard(ReportCard studentReportCard) {
		this.studentReportCard = studentReportCard;
	}
	
	public Long getId() {
		return id;
	}
	
	public Integer getCurrentSemester() {
		return currentSemester;
	}
	
	public void setCurrentSemester(Integer semester) {
		this.currentSemester = semester;
	}
	
}
