package com.wibmo.service;

import java.util.List;
import java.util.Map;

import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.dto.ReportCardResponseDTO;
import com.wibmo.entity.ReportCard;
import com.wibmo.entity.Student;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;

/**
 * 
 */
public interface ReportCardService {
	
	/**
	 * Uploads the given list of grade cards into the Database.
	 * 
	 * <b>Note</b>: This is a general method for both save and update functionality.
	 * 
	 * @param courseId
	 * @param studentIdToAssignedGradesMap
	 */
	public void addAll(List<ReportCardRequestDTO> reportCardRequestDTOs);
	
	/**
	 * 
	 * @param studentId
	 * @param courseId
	 * @return
	 * @throws UserNotFoundException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws StudentNotRegisteredForCourseException 
	 */
	public String getGradeByStudentIdAndCourseId(
			Integer studentId, Integer courseId) 
				throws 
					UserNotFoundException, 
					CourseNotExistsInCatalogException, 
					StudentNotRegisteredForCourseException;
	
	/**
	 * 
	 * @param studentId
	 * @param semester
	 * @return
	 * @throws UserNotFoundException 
	 * @throws StudentNotRegisteredForSemesterException 
	 */
	public List<ReportCardResponseDTO> getReportCardByStudentIdAndSemester(
			Integer studentId, Integer semester) 
				throws 
					UserNotFoundException, 
					StudentNotRegisteredForSemesterException;
	
	/**
	 * 
	 * @param studentId
	 * @return
	 * @throws UserNotFoundException 
	 */
	public Map<Integer, List<ReportCardResponseDTO>> getSemesterToReportCardMapByStudentId(Integer studentId)
			throws UserNotFoundException;
	
	/**
	 * 
	 * @param student
	 * @param courseId
	 * @return
	 */
	public ReportCardResponseDTO getReportCardByStudentForCourse(Student student, Integer courseId);

}
