package com.wibmo.bean;

import java.util.List;

public class Professor {
	private String professorName;
	private int professorID; 
	private List<Course> coursesTaught;
	/**
	 * @return the professorName
	 */
	public String getProfessorName() {
		return professorName;
	}
	/**
	 * @param professorName the professorName to set
	 */
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	/**
	 * @return the professorID
	 */
	public int getProfessorID() {
		return professorID;
	}
	/**
	 * @param professorID the professorID to set
	 */
	public void setProfessorID(int professorID) {
		this.professorID = professorID;
	}
	/**
	 * @return the coursesTaught
	 */
	public List<Course> getCoursesTaught() {
		return coursesTaught;
	}
	/**
	 * @param coursesTaught the coursesTaught to set
	 */
	public void setCoursesTaught(List<Course> coursesTaught) {
		this.coursesTaught = coursesTaught;
	}
	
	
}