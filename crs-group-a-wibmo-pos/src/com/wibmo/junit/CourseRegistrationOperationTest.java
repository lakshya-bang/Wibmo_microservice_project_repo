/**
 * 
 */
package com.wibmo.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.CourseRegistration;
import com.wibmo.bean.Student;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.business.UserOperationImpl;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.utils.ProfessorNotAssignedForCourseException;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.RegistrationStatus;
/**
 * 
 */
public class CourseRegistrationOperationTest {

	private CourseRegistrationOperation courseRegistrartionOperation;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		courseRegistrartionOperation = new CourseRegistrationOperationImpl(
				new UserOperationImpl(),
				new StudentOperationImpl(), 
				new ProfessorOperationImpl(), 
				new CourseOperationImpl(
					new UserOperationImpl(), 
					new ProfessorOperationImpl()));
	}
	
	/**
	 * junit test for register Operation
	 */
	
	@Test
	public void register_shouldTrowExceptionTest() {
		
		List<Integer> primaryCourses = new ArrayList<>(List.of());
		List<Integer> alternateCourses = new ArrayList<>(List.of(5,6));
		
		Student testStudent = new Student();
		testStudent.setStudentId(1001);
		testStudent.setCurrentSemester(1);
		
		assertThrows(StudentAlreadyRegisteredForSemesterException.class, 
				()->courseRegistrartionOperation.register(primaryCourses, alternateCourses, testStudent));
	}
	
	@Test
	public void register_shouldTrowExceptionTest1() {
		List<Integer> primaryCourses = new ArrayList<>(List.of(101,201,102,104));
		List<Integer> alternateCourses = new ArrayList<>(List.of(303, 305));
		Student testStudent = new Student();
		testStudent.setStudentId(5);
		testStudent.setCurrentSemester(3);
		
		assertThrows(CourseNotExistsInCatalogException.class, 
				()->courseRegistrartionOperation.register(primaryCourses, alternateCourses, testStudent));
	}
	
//	@Test
//	public void registerTest() {
//		
//	}
	
	/**
	 * junit test for viewRegisteredCoursesByStudent Operation
	 */
	@Test
	public void viewRegisteredCoursesByStudent_shouldThrowExceptionTest() {
		Student testStudent = new Student();
		testStudent.setStudentId(6);
		testStudent.setStudentEmail("testStd@email.com");
		testStudent.setStudentName("alice");
		testStudent.setCurrentSemester(2);
		
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.viewRegisteredCoursesByStudent(testStudent));
	}
	
//	@Test
//	public void viewRegisteredCoursesByStudentTest() {
//		
//	}
	
	/**
	 * junit test for getRegistrationStatusByStudent
	 */
	@Test
	public void getRegistrationStatusByStudent_shouldThrowExceptionTest() {
		Student testStudent = new Student();
		testStudent.setStudentId(123);
		testStudent.setStudentEmail("testStd@email.com");
		testStudent.setStudentName("testStd");
		testStudent.setCurrentSemester(2);
		
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.getRegistrationStatusByStudent(testStudent));
	}
	
	@Test
	public void getRegistrationStatusByStudentTest() {
		Student testSt = new Student();
		testSt.setStudentId(4);
		testSt.setCurrentSemester(3);
		RegistrationStatus expectedRegStatus = RegistrationStatus.APPROVED;
		RegistrationStatus actualRegStatus=null;
		try {
			actualRegStatus = courseRegistrartionOperation.getRegistrationStatusByStudent(testSt);
		} catch (StudentNotRegisteredForSemesterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedRegStatus, actualRegStatus);
	}
	
	/**
	 * junit test for addCourse
	 */
	@Test
	public void addCourse_shouldThrowExceptionTest() {
		Integer courseId = 4;
		Student testStudent = new Student();
		testStudent.setStudentId(6);
		testStudent.setCurrentSemester(2);
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest1() {
		Integer courseId = 4;
		Student testStudent = new Student();
		testStudent.setStudentId(4);
		testStudent.setStudentEmail("manali@test.com");
		testStudent.setStudentName("manali");
		testStudent.setCurrentSemester(3);
		assertThrows(StudentAlreadyRegisteredForCourseInSemesterException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest2() {
		Integer courseId = 6;
		Student testStudent = new Student();
		testStudent.setStudentId(5);
		testStudent.setStudentEmail("bob");
		testStudent.setStudentName("bob");
		testStudent.setCurrentSemester(3);
		assertThrows(StudentAlreadyRegisteredForAllAlternativeCoursesException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest3(){
		Integer courseId = 1127;
		Student testStudent = new Student();
		testStudent.setStudentId(4);
		testStudent.setStudentEmail("manali@test.com");
		testStudent.setStudentName("manali");
		testStudent.setCurrentSemester(3);
		assertThrows(StudentAlreadyRegisteredForAllPrimaryCoursesException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest4(){
		Integer courseId = 201;
		Student testStudent = new Student();
		testStudent.setStudentId(5);
		testStudent.setStudentEmail("bob");
		testStudent.setStudentName("bob");
		testStudent.setCurrentSemester(3);
		assertThrows(CourseNotExistsInCatalogException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
//	@Test
//	public void addCourseTest() {
//
//	}
	
	/**
	 * junit test for dropCourse
	 */
	@Test
	public void dropCourse_shouldThrowExceptionTest(){
		Integer courseId = 1;
		Student testStudent = new Student();
		testStudent.setStudentId(6);
		testStudent.setStudentEmail("alice");
		testStudent.setStudentName("alice");
		testStudent.setCurrentSemester(2);
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.dropCourse(courseId, testStudent));
	}
	@Test
	public void dropCourse_shouldThrowExceptionTest1(){
		Integer courseId = 3;
		Student testStudent = new Student();
		testStudent.setStudentId(5);
		testStudent.setStudentEmail("bob");
		testStudent.setStudentName("bob");
		testStudent.setCurrentSemester(3);
		assertThrows(StudentNotRegisteredForCourseInSemesterException.class,
				()->courseRegistrartionOperation.dropCourse(courseId, testStudent));
	}
	
//	@Test
//	public void dropCourseTest() {
//		
//	}
	
	/**
	 * junit test for getRegisteredStudentsByCourseId
	 */
	@Test
	public void getRegisteredStudentsByCourseId_shouldThrowExceptionTest1(){
		Integer courseId = 201;

		assertThrows(CourseNotExistsInCatalogException.class,
				()->courseRegistrartionOperation.getRegisteredStudentsByCourseId(courseId));
	}
	//error
	@Test
	public void getRegisteredStudentsByCourseIdTest(){
		Student st = null;
		Integer courseId = 3;
		List<Student> expectedstudent = new ArrayList<>();
		st = new Student();
		st.setStudentId(0);
		st.setStudentEmail("lakshya@test.com");
		st.setStudentName("Lakshya");
		st.setCurrentSemester(2);
		expectedstudent.add(st);
		st = new Student();
		st.setStudentId(4);
		st.setStudentEmail("manali@test.com");
		st.setStudentName("manali");
		st.setCurrentSemester(3);
		expectedstudent.add(st);
		
		List<Student> actualStudent = null;
		try {
			actualStudent = courseRegistrartionOperation.getRegisteredStudentsByCourseId(courseId);
		} catch (CourseNotExistsInCatalogException e) {
			e.printStackTrace();
		}
		
		assertEquals(expectedstudent, actualStudent);
	}
	
	/**
	 * junit test for getCourseIdToRegisteredStudentsMappingByProfessorId
	 */
//	@Test
//	public void getCourseIdToRegisteredStudentsMappingByProfessorIdTest(){
//		Integer ProfessorId = 1;
//		Map<Integer, List<Student>> courseIdToRegisteredStudentsMap = new HashMap<>();
//		
//		
//	}
	
	
	/**
	 * junit test for viewCourseRegistrationByRegistrationStatus
	 */
//	@Test
	public void viewCourseRegistrationByRegistrationStatus_shouldThrowExceptionTest1(){
		RegistrationStatus regSt = RegistrationStatus.PENDING;
		RegistrationStatus expectedRegStatus = RegistrationStatus.PENDING;
		RegistrationStatus actualRegStatus=null;
		courseRegistrartionOperation.viewCourseRegistrationByRegistrationStatus(regSt);
		actualRegStatus = RegistrationStatus.PENDING;
		assertEquals(expectedRegStatus, actualRegStatus);
		}
	
//	@Test
//	public void viewCourseRegistrationByRegistrationStatusTest(){
//		
//	}
	
	/**
	 * junit test for approveRegistrationByRegistrationId
	 */
	@Test
	public void approveRegistrationByRegistrationIdTest(){
		int courseRegId = 3;
		Boolean expectedValue = Boolean.TRUE;
		Boolean actualValue = courseRegistrartionOperation
				.updateCourseRegistrationStatusToByRegistrationIds(
					RegistrationStatus.APPROVED, 
					Set.of(courseRegId));
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * junit test for rejectRegistrationByRegistrationId
	 */
	@Test
	public void rejectRegistrationByRegistrationIdTest(){
		int courseRegId = 2;
		Boolean expectedValue = Boolean.TRUE;
		Boolean actualValue = courseRegistrartionOperation
				.updateCourseRegistrationStatusToByRegistrationIds(
					RegistrationStatus.REJECTED, 
					Set.of(courseRegId));
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * viewRegisteredStudentsByProfessorIdAndCourseId
	 * CourseNotExistsInCatalogException, 
				ProfessorNotExistsInSystemException,
				ProfessorNotAssignedForCourseException;
	 */
	@Test
	public void viewRegisteredStudentsByProfessorIdAndCourseId_shouldThrowExceptionTest(){
		Integer courseId = 201;
		Integer professorId = 1;
		assertThrows(
				CourseNotExistsInCatalogException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}
	@Test
	public void viewRegisteredStudentsByProfessorIdAndCourseId_shouldThrowExceptionTest1(){
		Integer courseId = 7;
		Integer professorId = 4;
		assertThrows(
				UserNotFoundException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}
	@Test
	public void viewRegisteredStudentsByProfessorIdAndCourseId_shouldThrowExceptionTest2(){
		Integer courseId = 7;
		Integer professorId = 3;
		assertThrows(ProfessorNotAssignedForCourseException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}

	@Test
//	public void viewRegisteredStudentsByProfessorIdAndCourseIdTest(){
//		
//	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	

}
