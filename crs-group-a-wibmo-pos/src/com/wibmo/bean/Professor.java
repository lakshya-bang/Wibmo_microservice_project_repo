package com.wibmo.bean;

import java.util.List;

public class Professor {
	private String professorName;
	private int professorID; 
	private List<Course> coursesTaught;
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String name) {
		professorName = name;
	}
	public int getProfessorID() {
		return professorID;
	}
	public void setProfessorID(int iD) {
		professorID = iD;
	}
	public List<Course> getCoursesTaught() {
		return coursesTaught;
	}
	public void setCoursesTaught(List<Course> coursesTaught) {
		this.coursesTaught = coursesTaught;
	}
	
}
