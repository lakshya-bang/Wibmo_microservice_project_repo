package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.dto.ReportCardResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Student;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotAvailableDueToSeatsFullException;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.InvalidCourseForCourseTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllCoursesOfTypeException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotEligibleForCourseRegistrationException;
import com.wibmo.exception.StudentNotRegisteredForCourseException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;

/**
 * Defines the contracts that every implementation of
 * Student Service should provide.
 */
public interface StudentService {

	/**
	 * Gets the Student associated with the given Id.
	 * 
	 * @param studentId the Id for which the Student Entity will be fetched.
	 * @return Student Entity if the student is found, null otherwise.
	 */
	public Student getStudentById(Integer studentId);
	
	/**
	 * Gets all students in the Database
	 * 
	 * @return
	 */
	public List<Student> getAllStudents();

	/**
	 * Adds the given Student Entity into the Database
	 * 
	 * @param student
	 */
	public void add(Student student);

	/**
	 * Checks whether a Student with the given Id exists in the Database.
	 * 
	 * @param studentId
	 * @return true if the student exists with this id, false otherwise. 
	 */
	public Boolean isStudentExistsById(Integer studentId);
	
	/**
	 * Fetches the Student Entities associated with the given studentIds.
	 * 
	 * @param studentIds the collection of ids to fetch.
	 * @return List of Student Entities for the given ids.
	 */
	public List<Student> getAllStudentsByIds(Collection<Integer> studentIds);

	/**
	 * An efficient approach to have constant loop-up 
	 * for existence of a Student for a given id.
	 * 
	 * @param studentIds the collection of ids to fetch.
	 * @return the Map of studentId -> Student pairs.
	 */
	public Map<Integer, Student> getStudentIdToStudentMap(Collection<Integer> studentIds);

	/**
	 * Fetches the current semester of the Student associated with the given id.
	 * 
	 * @param studentId the id for which the Student will be fetched.
	 * @return the current semester of the student
	 * @throws UserNotFoundException if the Student for the given id does not exist.
	 */
	public Integer getCurrentSemesterByStudentId(Integer studentId) throws UserNotFoundException;

	/********************************** Course Methods ***********************************/
	
	/**
	 * Fetches the Course Details for the given courseId
	 * 
	 * @param courseId
	 * @return
	 */
	public CourseResponseDTO getCourseDetailsById(Integer courseId);
	
	/**
	 * 
	 * 
	 * @param courseIds
	 * @return
	 */
	List<CourseResponseDTO> getCourseDetailsByIds(Collection<Integer> courseIds);
	
	/**
	 * 
	 * @param currentSemester
	 * @return
	 */
	public List<CourseResponseDTO> getCourseDetailsBySemester(Integer semester);
	
	/**
	 * 
	 * @param courseIds
	 * @return
	 */
	public Map<Integer, Course> getCourseIdToCourseMap(Collection<Integer> courseIds);
	
	/**
	 * 
	 * @param courseId
	 * @return
	 * @throws CourseNotExistsInCatalogException 
	 */
	public CourseType getCourseTypeByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException;
	
	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public Boolean isCourseExistsInCatalog(Integer courseId);
	
	/**
	 * 
	 * @return
	 */
	public List<CourseResponseDTO> getAllCourses();

	/**
	 * 
	 * @param courseIds
	 */
	public void decrementNumOfSeatsByCourseIds(List<Integer> courseIds);

	/**
	 * 
	 * @param courseId
	 * @return
	 */
	public Boolean isCourseHasVacantSeats(Integer courseId);
	
	/************************* Course-Registration Methods ***************************/
	
	/**
	 * Registers the student for the given courses.
	 * 
	 * @param courseRegistrationRequestDTO
	 * @throws StudentAlreadyRegisteredForSemesterException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 * @throws StudentNotEligibleForCourseRegistrationException
	 * @throws InvalidCourseForCourseTypeException 
	 * @throws CourseNotAvailableDueToSeatsFullException 
	 */
	public void register(CourseRegistrationRequestDTO courseRegistrationRequestDTO) 
			throws 
				StudentAlreadyRegisteredForSemesterException, 
				CourseNotExistsInCatalogException, 
				UserNotFoundException, 
				StudentNotEligibleForCourseRegistrationException,
				InvalidCourseForCourseTypeException,
				CourseNotAvailableDueToSeatsFullException;
	
	/**
	 * Fetches the Courses registered by the student for the given semester.
	 * 
	 * @param studentId
	 * @param semOfStudy
	 * @return 
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 */
	public List<CourseResponseDTO> getRegisteredCoursesByStudentIdAndSemester(
			Integer studentId, Integer semester)
			throws 
				StudentNotRegisteredForSemesterException, 
				UserNotFoundException, 
				CourseNotExistsInCatalogException;
	
	/**
	 * 
	 * @param student
	 * @return
	 * @throws StudentNotRegisteredForSemesterException 
	 */
	public RegistrationStatus getRegistrationStatusByStudentIdAndSemester(Integer studentId, Integer semester) 
			throws StudentNotRegisteredForSemesterException;
	
	/**
	 * 
	 * 
	 * @param courseId
	 * @param student
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws StudentAlreadyRegisteredForCourseInSemesterException 
	 * @throws StudentAlreadyRegisteredForAllCoursesOfTypeException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws UserNotFoundException 
	 */
	public void addCourse(Integer courseId, Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentAlreadyRegisteredForCourseInSemesterException,
				StudentAlreadyRegisteredForAllCoursesOfTypeException,
				CourseNotExistsInCatalogException, 
				UserNotFoundException;
	
	/**
	 * Drops the given course from the registered list of courses for the given semester.
	 * 
	 * @param courseId
	 * @param student
	 * @throws StudentNotRegisteredForSemesterException 
	 * @throws StudentNotRegisteredForCourseInSemesterException 
	 * @throws CourseNotExistsInCatalogException 
	 */
	public void dropCourse(Integer courseId, Integer studentId, Integer semester) 
			throws 
				StudentNotRegisteredForSemesterException, 
				StudentNotRegisteredForCourseInSemesterException, 
				CourseNotExistsInCatalogException;
	
	/**
	 * 
	 * @param courseId
	 * @throws CourseNotExistsInCatalogException 
	 */
	public List<Student> getRegisteredStudentsByCourseId(Integer courseId) 
			throws CourseNotExistsInCatalogException;

	/**
	 * 
	 * @param regStatus
	 * @return 
	 */
	public List<CourseRegistrationResponseDTO> getCourseRegistrationsByRegistrationStatus(
			RegistrationStatus registrationStatus);
	
	/**
	 * 
	 * @param studentId
	 * @param courseId
	 * @return
	 * @throws UserNotFoundException 
	 * @throws CourseNotExistsInCatalogException 
	 */
	public Boolean hasRegistrationByStudentIdAndCourseId(
			Integer studentId, Integer courseId) 
				throws 
					UserNotFoundException, 
					CourseNotExistsInCatalogException;

	/**
	 * 
	 * @param studentId
	 * @param semester
	 * @return
	 * @throws UserNotFoundException 
	 */
	public Boolean hasRegistrationByStudentIdAndSemester(
			Integer studentId, Integer semester) 
					throws UserNotFoundException;

	/**
	 * 
	 * @param studentId
	 * @param semester
	 * @return
	 */
	public CourseRegistration getCourseRegistrationByStudentIdAndSemester(
			Integer studentId, Integer semester);

	/**
	 * 
	 * @param registrationId
	 * @return
	 */
	List<Integer> getRegisteredCourseIdsByRegistrationId(Integer registrationId);
	
	/**************************** Report Card Methods **********************************/
	
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
	 * This method should be called when we need to generate 
	 * Report Card for All Semesters for the given studentId.
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
	 * @throws UserNotFoundException 
	 * @throws CourseNotExistsInCatalogException 
	 * @throws StudentNotRegisteredForCourseException 
	 */
	public ReportCardResponseDTO getReportCardByStudentIdAndCourseId(Integer studentId, Integer courseId)
			throws 
				UserNotFoundException, 
				CourseNotExistsInCatalogException, 
				StudentNotRegisteredForCourseException;
	
}