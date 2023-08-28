package com.wibmo.dao;

import java.util.List;

import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;

/**
 * 
 */
public interface ReportCardDAO {

	/**
	 * 
	 * @param reportCard
	 */
	public void save(ReportCard reportCard);

	/**
	 * 
	 * @param studentId
	 * @return
	 */
    public List<ReportCard> findAllByStudentId(Integer studentId);

    /**
     * This method returns the ReportCard object
     * for the given student and courseId.
     * This method "strictly" returns the ReportCard if only
     * the course was registered in the current semester,
     * otherwise returns null.
     * 
     * TODO: Merge findByStudentAndCourseId() and findByStudentIdAndCourseId()
     * into findByStudentIdAndSemesterAndCourseId()
     * 
     * @param student
     * @param courseId
     * @return
     */
	public ReportCard findByStudentAndCourseId(Student student, Integer courseId);
	
	/**
	 * This method returns the ReportCard object
     * for the given studentId and courseId.
	 * 
	 * TODO: Merge findByStudentAndCourseId() and findByStudentIdAndCourseId()
     * into findByStudentIdAndSemesterAndCourseId()
	 * 
	 * @param studentId
	 * @param courseId
	 * @return
	 */
	public ReportCard findByStudentIdAndCourseId(Integer studentId, Integer courseId);

	/**
	 * 
	 * @param studentId
	 * @param semester
	 * @return
	 */
	List<ReportCard> findByStudentIdAndSemester(Integer studentId, Integer semester);
	
}
