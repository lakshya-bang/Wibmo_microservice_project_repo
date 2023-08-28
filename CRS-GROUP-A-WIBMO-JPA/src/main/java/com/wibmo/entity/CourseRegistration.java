/**
 * 
 */
package com.wibmo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
@Entity
@Table(name = "registered_courses")
public class CourseRegistration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reg_id")
	private Integer registrationId;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "semester")
	private Integer semester;
	
	@Column(name = "primary_course_1_id")
	private Integer primaryCourse1Id;

    @Column(name = "primary_course_2_id")
    private Integer primaryCourse2Id;

    @Column(name = "primary_course_3_id")
    private Integer primaryCourse3Id;

    @Column(name = "primary_course_4_id")
    private Integer primaryCourse4Id;

    @Column(name = "alternative_course_1_id")
    private Integer alternativeCourse1Id;

    @Column(name = "alternative_course_2_id")
    private Integer alternativeCourse2Id;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;
	    
	@Column(name = "reg_status")
//	@NotNull
	@Enumerated(EnumType.STRING)
	private RegistrationStatus registrationStatus = RegistrationStatus.PENDING;

	public CourseRegistration() {}
	
	public CourseRegistration(
			Integer registrationId, 
			Integer studentId, 
			Integer semester, 
			Integer year,
			Integer primaryCourse1Id,
			Integer primaryCourse2Id, 
			Integer primaryCourse3Id, 
			Integer primaryCourse4Id, 
			Integer alternativeCourse1Id,
			Integer alternativeCourse2Id, 
			RegistrationStatus registrationStatus) {
		this.registrationId = registrationId;
		this.studentId = studentId;
		this.semester = semester;
		this.year = year;
		this.primaryCourse1Id = primaryCourse1Id;
		this.primaryCourse2Id = primaryCourse2Id;
		this.primaryCourse3Id = primaryCourse3Id;
		this.primaryCourse4Id = primaryCourse4Id;
		this.alternativeCourse1Id = alternativeCourse1Id;
		this.alternativeCourse2Id = alternativeCourse2Id;
		this.registrationStatus = registrationStatus;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "student_id", referencedColumnName = "student_id", insertable = false, updatable = false)
//	private Student student;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "primary_course_1_id", referencedColumnName = "course_id", insertable = false, updatable = false)
//	private Course primaryCourse1;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "primary_course_2_id", referencedColumnName = "course_id", insertable = false, updatable = false)
//  private Course primaryCourse2;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "primary_course_3_id", referencedColumnName = "course_id", insertable = false, updatable = false)
//  private Course primaryCourse3;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "primary_course_4_id", referencedColumnName = "course_id", insertable = false, updatable = false)
//  private Course primaryCourse4;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "alternative_course_1_id", referencedColumnName = "course_id", insertable = false, updatable = false)
//  private Course alternativeCourse1;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "alternative_course_2_id", referencedColumnName = "course_id", insertable = false, updatable = false)
//  private Course alternativeCourse2;

	
	/**
	 * @return the registrationId
	 */
	public Integer getRegistrationId() {
		return registrationId;
	}

	/**
	 * @param registrationId the registrationId to set
	 */
	public void setRegistrationId(Integer registrationId) {
		this.registrationId = registrationId;
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
	 * @return the primaryCourse1Id
	 */
	public Integer getPrimaryCourse1Id() {
		return primaryCourse1Id;
	}

	/**
	 * @param primaryCourse1Id the primaryCourse1Id to set
	 */
	public void setPrimaryCourse1Id(Integer primaryCourse1Id) {
		this.primaryCourse1Id = primaryCourse1Id;
	}

	/**
	 * @return the primaryCourse2Id
	 */
	public Integer getPrimaryCourse2Id() {
		return primaryCourse2Id;
	}

	/**
	 * @param primaryCourse2Id the primaryCourse2Id to set
	 */
	public void setPrimaryCourse2Id(Integer primaryCourse2Id) {
		this.primaryCourse2Id = primaryCourse2Id;
	}

	/**
	 * @return the primaryCourse3Id
	 */
	public Integer getPrimaryCourse3Id() {
		return primaryCourse3Id;
	}

	/**
	 * @param primaryCourse3Id the primaryCourse3Id to set
	 */
	public void setPrimaryCourse3Id(Integer primaryCourse3Id) {
		this.primaryCourse3Id = primaryCourse3Id;
	}

	/**
	 * @return the primaryCourse4Id
	 */
	public Integer getPrimaryCourse4Id() {
		return primaryCourse4Id;
	}

	/**
	 * @param primaryCourse4Id the primaryCourse4Id to set
	 */
	public void setPrimaryCourse4Id(Integer primaryCourse4Id) {
		this.primaryCourse4Id = primaryCourse4Id;
	}

	/**
	 * @return the alternativeCourse1Id
	 */
	public Integer getAlternativeCourse1Id() {
		return alternativeCourse1Id;
	}

	/**
	 * @param alternativeCourse1Id the alternativeCourse1Id to set
	 */
	public void setAlternativeCourse1Id(Integer alternativeCourse1Id) {
		this.alternativeCourse1Id = alternativeCourse1Id;
	}

	/**
	 * @return the alternativeCourse2Id
	 */
	public Integer getAlternativeCourse2Id() {
		return alternativeCourse2Id;
	}

	/**
	 * @param alternativeCourse2Id the alternativeCourse2Id to set
	 */
	public void setAlternativeCourse2Id(Integer alternativeCourse2Id) {
		this.alternativeCourse2Id = alternativeCourse2Id;
	}

	/**
	 * @return the studentId
	 */
	public Integer getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the registrationStatus
	 */
	public RegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}

	/**
	 * @param registrationStatus the registrationStatus to set
	 */
	public void setRegistrationStatus(RegistrationStatus registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	 

	
}
