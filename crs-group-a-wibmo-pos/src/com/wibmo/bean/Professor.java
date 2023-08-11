package com.wibmo.bean;

import java.util.List;

public class Professor {
	private String Name;
	private String ID; 
	private List<Course> coursesTaught;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public List<Course> getCoursesTaught() {
		return coursesTaught;
	}
	public void setCoursesTaught(List<Course> coursesTaught) {
		this.coursesTaught = coursesTaught;
	}
	
}
