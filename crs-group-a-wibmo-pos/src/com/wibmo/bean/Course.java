package com.wibmo.bean;

import java.util.List;

import java.util.List;

public class Course {
	private int courseID;
	private String courseTitle;
	private String department;
	private Professor assignedProfessor;
	private List<Course> prerequisites;
	private boolean isCancelled;
	private boolean isFilled;
	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}
	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}
	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the assignedProfessor
	 */
	public Professor getAssignedProfessor() {
		return assignedProfessor;
	}
	/**
	 * @param assignedProfessor the assignedProfessor to set
	 */
	public void setAssignedProfessor(Professor assignedProfessor) {
		this.assignedProfessor = assignedProfessor;
	}
	/**
	 * @return the prerequisites
	 */
	public List<Course> getPrerequisites() {
		return prerequisites;
	}
	/**
	 * @param prerequisites the prerequisites to set
	 */
	public void setPrerequisites(List<Course> prerequisites) {
		this.prerequisites = prerequisites;
	}
	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}
	/**
	 * @param isCancelled the isCancelled to set
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	/**
	 * @return the isFilled
	 */
	public boolean isFilled() {
		return isFilled;
	}
	/**
	 * @param isFilled the isFilled to set
	 */
	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}
	
	
}
