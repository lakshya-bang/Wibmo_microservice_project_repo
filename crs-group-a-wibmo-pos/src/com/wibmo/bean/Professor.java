package com.wibmo.bean;

import java.util.List;

public class Professor {
	private String professorName;
	private long professorID; 
	private List<Integer> coursesTaught;
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
	public long getProfessorID() {
		return professorID;
	}
	/**
	 * @param professorID the professorID to set
	 */
	public void setProfessorID(long professorID) {
		this.professorID = professorID;
	}
	/**
	 * @return the coursesTaught
	 */
	public List<Integer> getCoursesTaught() {
		return coursesTaught;
	}
	/**
	 * @param list the coursesTaught to set
	 */
	public void setCoursesTaught(List<Integer> list) {
		this.coursesTaught = list;
	}
	
	
}
