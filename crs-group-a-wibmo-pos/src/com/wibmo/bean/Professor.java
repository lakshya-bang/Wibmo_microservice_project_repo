/**
 * 
 */
package com.wibmo.bean;

import java.util.ArrayList;
import java.util.List;

import com.wibmo.dao.CourseDAO;
import com.wibmo.dao.CourseDAOImpl;

/**
 * Table name = professor
 * FOREIGN KEY(professor_id) REFERENCES User(user_id)
 */
public class Professor {

	private Integer professorId;	// professor_id
	private String department;		// department
	private List<Course> coursesTaught; 

	public Professor() {}
	
	public Professor(Integer professorId, String department) {
		this.professorId = professorId;
		this.department = department;
	}

	/**
	 * @return the professorId
	 */
	public Integer getProfessorId() {
		return professorId;
	}

	/**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
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
	 * @return the list of Courses Taught
	 */

	public List<Course> getCoursesTaught() {
		return coursesTaught;
	}

	/**
	 * @param ArrayList<Integer> coursesTaught 
	 */

	public void setCoursesTaught(ArrayList<Course> coursesTaught) {
		CourseDAO courseDao = new CourseDAOImpl();
		this.coursesTaught = courseDao.findCourseByProfessorID(professorId);
	}

	@Override
	public String toString() {
		return "Professor [professorId=" + professorId + ", department=" + department + "]";
	}

	
}
