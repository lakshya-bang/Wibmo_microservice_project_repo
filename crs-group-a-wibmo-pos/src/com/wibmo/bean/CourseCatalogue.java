package com.wibmo.bean;

/**
 * 
 */
public class CourseCatalogue {

	private Long courseId;
	private Long professorId;
	private Integer availableSeats;
	
	public CourseCatalogue(Long courseId, Long professorId, Integer availableSeats) {
		this.courseId = courseId;
		this.professorId = professorId;
		this.availableSeats = availableSeats;
	}
	
	/**
	 * @return the courseId
	 */
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the professorId
	 */
	public Long getProfessorId() {
		return professorId;
	}
	/**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}
	/**
	 * @return the availableSeats
	 */
	public Integer getAvailableSeats() {
		return availableSeats;
	}
	/**
	 * @param availableSeats the availableSeats to set
	 */
	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	@Override
	public String toString() {
		return "[courseId=" + courseId + ", professorId=" + professorId + ", availableSeats="
				+ availableSeats + "]";
	}
	
	
}
