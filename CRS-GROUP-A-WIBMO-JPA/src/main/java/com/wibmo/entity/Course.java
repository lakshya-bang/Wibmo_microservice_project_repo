/**
 * 
 */
package com.wibmo.entity;

import java.lang.annotation.Inherited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.wibmo.entity.Professor;
import com.wibmo.enums.CourseType;

/**
 * 
 */
@Entity
@Table(name = "course")
public class Course {
    @Column(name = "course_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer courseId;		// course_id (PK)

	@Column(name = "course_title")
    @NotNull
	private String courseTitle;		// course_title
    @Column(name = "semester")
    @NotNull
	private Integer semester;		// semester
    @Column(name = "year")
    @NotNull
	private Integer year;			// year
    @Column(name = "department")
    @NotNull
	private String department;		// department
    
    @Column(name = "professor_id", insertable=false, updatable = false)
    private Integer professorId;
    
    /**
	 * @param professorId the professorId to set
	 */
	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
	}

	@ManyToOne
    @JoinColumn(name = "professor_id")
	private Professor professor = null;	// professor_id
    @Column(name = "is_cancelled")
    @NotNull
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean isCancelled = false;	// is_cancelled
    @Column(name = "no_of_seats")
    @NotNull
	private Integer noOfSeats = 10;		// no_of_seats
    @Column(name = "course_type")
    @NotNull
    @Enumerated(EnumType.STRING)
	private CourseType courseType;	// course_type

    @Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseTitle=" + courseTitle + ", semester=" + semester + ", year="
				+ year + ", department=" + department + ", professorId=" //Add here 
				+ ", isCancelled=" + isCancelled
				+ ", noOfSeats=" + noOfSeats + ", courseType=" + courseType + "]";
	}
    
    /**
   	 * @return the courseId
   	 */
   	public Integer getCourseId() {
   		return courseId;
   	}

   	/**
   	 * @param courseId the courseId to set
   	 */
   	public void setCourseId(Integer courseId) {
   		this.courseId = courseId;
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
   	 * @return the semester
   	 */
   	public Integer getSemester() {
   		return semester;
   	}

   	/**
   	 * @param semester the semester to set
   	 */
   	public void setSemester(Integer semester) {
   		this.semester = semester;
   	}

   	/**
   	 * @return the year
   	 */
   	public Integer getYear() {
   		return year;
   	}

   	/**
   	 * @param year the year to set
   	 */
   	public void setYear(Integer year) {
   		this.year = year;
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
   	 * @return the isCancelled
   	 */
   	public Boolean getIsCancelled() {
   		return isCancelled;
   	}

   	/**
   	 * @param isCancelled the isCancelled to set
   	 */
   	public void setIsCancelled(Boolean isCancelled) {
   		this.isCancelled = isCancelled;
   	}

   	/**
   	 * @return the noOfSeats
   	 */
   	public Integer getNoOfSeats() {
   		return noOfSeats;
   	}

   	/**
   	 * @param noOfSeats the noOfSeats to set
   	 */
   	public void setNoOfSeats(Integer noOfSeats) {
   		this.noOfSeats = noOfSeats;
   	}

   	/**
   	 * @return the courseType
   	 */
   	public CourseType getCourseType() {
   		return courseType;
   	}

   	/**
   	 * @param courseType the courseType to set
   	 */
   	public void setCourseType(CourseType courseType) {
   		this.courseType = courseType;
   	}
   	
   	public Integer getProfessorId() {
   		return professor.getProfessorId();
   	}
	
}