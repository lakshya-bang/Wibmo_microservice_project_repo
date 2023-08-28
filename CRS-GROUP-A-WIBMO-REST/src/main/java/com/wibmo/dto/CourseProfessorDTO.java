/**
 * 
 */
package com.wibmo.dto;

import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;

/**
 * 
 */
public class CourseProfessorDTO {
	
	private Course course;
	private Professor professor;
	
	public CourseProfessorDTO(Course course, Professor professor) {
		this.course = course;
		this.professor = professor;
	}
	
	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}
	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	/**
	 * @return the professor
	 */
	public Professor getProfessor() {
		return professor;
	}
	/**
	 * @param professor the professor to set
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	
}
