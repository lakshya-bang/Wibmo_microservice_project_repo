package com.wibmo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.exception.CannotAddGradeStudentRegistrationNotApprovedException;
import com.wibmo.exception.CourseIdCannotBeEmptyException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.GradeCannotBeEmptyException;
import com.wibmo.exception.GradeValueInvalidException;
import com.wibmo.exception.StudentIdCannotBeEmptyException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.UserNotFoundException;

/**
 * @author lakshya.bang
 */
public interface ProfessorService {
	
	/**
	 * Fetches the data of the professor saved in the DB by utilising Id as the index.
	 * @param userId
	 * @return	Professor
	 * @throws UserNotFoundException 
	 */
	public Professor getProfessorById(Integer userId) throws UserNotFoundException;
	
	/**
	 * Using the professor Ids fetches the professor details and returns the data as a Map.
	 * @param professorIds
	 * @return Map<Integer,Professor>
	 */
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds);

	/**
	 * Creates a new entry in the professor table.
	 * @param professor
	 */
	public void add(Professor professor);
	
	/**
	 * Returns the list of students registered under a professor.
	 * @param professorId
	 * @return
	 * @throws UserNotFoundException
	 */

	Map<Integer, List<Student>> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId)
			throws UserNotFoundException;

	/**
	 * Adding Grade details to the DB.
	 * @param reportCardRequestDTOs
	 * @throws StudentNotRegisteredForCourseException
	 * @throws CannotAddGradeStudentRegistrationNotApprovedException
	 * @throws UserNotFoundException
	 * @throws CourseNotExistsInCatalogException
	 * @throws StudentIdCannotBeEmptyException
	 * @throws CourseIdCannotBeEmptyException
	 * @throws GradeCannotBeEmptyException
	 * @throws GradeValueInvalidException
	 */
	void addAll(List<ReportCardRequestDTO> reportCardRequestDTOs)
			throws StudentNotRegisteredForCourseException, CannotAddGradeStudentRegistrationNotApprovedException,
			UserNotFoundException, CourseNotExistsInCatalogException, StudentIdCannotBeEmptyException,
			CourseIdCannotBeEmptyException, GradeCannotBeEmptyException, GradeValueInvalidException;

}
