/**
 * 
 */
package com.wibmo.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wibmo.application.CrsGroupAWibmoRestApplication;
import com.wibmo.bean.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.ProfessorNotAssignedForCourseException;
import com.wibmo.exception.ProfessorNotExistsInSystemException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.service.CourseOperation;
import com.wibmo.service.CourseOperationImpl;
import com.wibmo.service.CourseRegistrationOperation;
import com.wibmo.service.CourseRegistrationOperationImpl;
import com.wibmo.service.ProfessorOperation;
import com.wibmo.service.ProfessorOperationImpl;
import com.wibmo.service.StudentOperation;
import com.wibmo.service.StudentOperationImpl;

/**
 * @author manali.kumari
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class CourseRegistrationOperationTest {
	
	@Autowired
	private CourseRegistrationOperationImpl courseRegistrartionOperation;

	@Test
	public void register_shouldTrowExceptionTest() {
		List<Integer> primaryCourses = new ArrayList<>(List.of(11,2,8,16));
		List<Integer> alternateCourses = new ArrayList<>(List.of(10,5));
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
		testStudent.setStudentId(1011);
		testStudent.setCurrentSemester(1);
		
		assertThrows(CourseNotExistsInCatalogException.class, 
				()->courseRegistrartionOperation.register(primaryCourses, alternateCourses, testStudent));
	}
	
	@Test
	public void registerTest() {
		List<Integer> primaryCourses = new ArrayList<>(List.of(11,2,8,16));
		List<Integer> alternateCourses = new ArrayList<>(List.of(10,5));
		Student testStudent = new Student(1010, "mohit@stu.user.com", "Mohit", 1);
		try {
			courseRegistrartionOperation.register(primaryCourses, alternateCourses, testStudent);
		} catch (StudentAlreadyRegisteredForSemesterException e) {
			e.printStackTrace();
		} catch (CourseNotExistsInCatalogException e) {
			e.printStackTrace();
		}
		RegistrationStatus expectedRegStatus = RegistrationStatus.PENDING;
		RegistrationStatus actualRegStatus = null;
		try {
			actualRegStatus = courseRegistrartionOperation.getRegistrationStatusByStudent(testStudent);
		} catch (StudentNotRegisteredForSemesterException e) {
			e.printStackTrace();
		}
		
		assertEquals(expectedRegStatus, actualRegStatus);
	}
	
	/**
	 * junit test for viewRegisteredCoursesByStudent Operation
	 */
	@Test
	public void viewRegisteredCoursesByStudent_shouldThrowExceptionTest() {
		Student testStudent = new Student();
		testStudent.setStudentId(1010);
		testStudent.setStudentEmail("mohit@stu.user.com");
		testStudent.setStudentName("Mohit");
		testStudent.setCurrentSemester(1);
		
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.viewRegisteredCoursesByStudent(testStudent));
	}
//	@Test
//	public void viewRegisteredCourseByStudentTest() {
//		
//	}
	
	/**
	 * junit test for getRegistrationStatusByStudent
	 */
	@Test
	public void getRegistrationStatusByStudent_shouldThrowExceptionTest() {
		Student testStudent = new Student();
		testStudent.setStudentId(1010);
		testStudent.setStudentEmail("mohit@stu.user.com");
		testStudent.setStudentName("Mohit");
		testStudent.setCurrentSemester(1);
		
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.getRegistrationStatusByStudent(testStudent));
	}
	
	@Test
	public void getRegistrationStatusByStudentTest() {
		Student testSt = new Student();
		testSt.setStudentId(1008);
		testSt.setCurrentSemester(1);
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
		Integer courseId = 2;
		Student testStudent = new Student();
		testStudent.setStudentId(1017);
		testStudent.setCurrentSemester(1);
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest1() {
		Integer courseId = 16;
		Student testStudent = new Student();
		testStudent.setStudentId(1008);
		testStudent.setCurrentSemester(1);
		assertThrows(StudentAlreadyRegisteredForCourseInSemesterException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest2() {
		Integer courseId = 12;
		Student testStudent = new Student();
		testStudent.setStudentId(1008);
		testStudent.setCurrentSemester(1);
		assertThrows(StudentAlreadyRegisteredForAllAlternativeCoursesException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest3(){
		Integer courseId = 11;
		Student testStudent = new Student();
		testStudent.setStudentId(1008);
		testStudent.setCurrentSemester(1);
		assertThrows(StudentAlreadyRegisteredForAllPrimaryCoursesException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	@Test
	public void addCourse_shouldThrowExceptionTest4(){
		Integer courseId = 201;
		Student testStudent = new Student();
		testStudent.setStudentId(1017);
		testStudent.setCurrentSemester(1);
		assertThrows(CourseNotExistsInCatalogException.class,
				()->courseRegistrartionOperation.addCourse(courseId, testStudent));
	}
	
	/**
	 * junit test for dropCourse
	 */
	@Test
	public void dropCourse_shouldThrowExceptionTest(){
		Integer courseId = 10;
		Student testStudent = new Student();
		testStudent.setStudentId(1012);
		testStudent.setCurrentSemester(1);
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.dropCourse(courseId, testStudent));
	}
	@Test
	public void dropCourse_shouldThrowExceptionTest1(){
		Integer courseId = 11;
		Student testStudent = new Student();
		testStudent.setStudentId(1008);
		testStudent.setCurrentSemester(1);
		assertThrows(StudentNotRegisteredForCourseInSemesterException.class,
				()->courseRegistrartionOperation.dropCourse(courseId, testStudent));
	}
	
	/**
	 * junit test for getRegisteredStudentsByCourseId
	 */
	@Test
	public void getRegisteredStudentsByCourseId_shouldThrowExceptionTest(){
		Integer courseId = 201;

		assertThrows(CourseNotExistsInCatalogException.class,
				()->courseRegistrartionOperation.getRegisteredStudentsByCourseId(courseId));
	}
	//error
	@Test
	public void getRegisteredStudentsByCourseIdTest(){
		Student st = null;
		Integer courseId = 2;
		List<Student> expectedstudent = new ArrayList<>();
		st = new Student();
		st.setStudentId(1001);
		st.setCurrentSemester(1);
		expectedstudent.add(st);
		st = new Student();
		st.setStudentId(1008);
		st.setCurrentSemester(1);
		expectedstudent.add(st);
		st = new Student();
		st.setStudentId(1009);
		st.setCurrentSemester(1);
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
//	public void viewCourseRegistrationByRegistrationStatus_shouldThrowExceptionTest1(){
//		RegistrationStatus regSt = RegistrationStatus.PENDING;
//		RegistrationStatus expectedRegStatus = RegistrationStatus.PENDING;
//		RegistrationStatus actualRegStatus=null;
//		try {
//			actualRegStatus = courseRegistrartionOperation.viewCourseRegistrationByRegistrationStatus(regSt);
//		} catch (StudentNotRegisteredForSemesterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		assertEquals(expectedRegStatus, actualRegStatus);
//		}
	
	
	/**
	 * junit test for approveRegistrationByRegistrationId
	 */
	@Test
	public void approveRegistrationByRegistrationIdTest(){
		int courseRegId = 1;
		boolean expectedValue = true;
		boolean actualValue = courseRegistrartionOperation.approveRegistrationByRegistrationId(courseRegId);
		assertEquals(expectedValue, actualValue);
	}
	
	/**
	 * junit test for rejectRegistrationByRegistrationId
	 */
	@Test
	public void rejectRegistrationByRegistrationIdTest(){
		int courseRegId = 4;
		boolean expectedValue = true;
		boolean actualValue = courseRegistrartionOperation.rejectRegistrationByRegistrationId(courseRegId);
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
		Integer professorId = 1006;
		assertThrows(CourseNotExistsInCatalogException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}
	@Test
	public void viewRegisteredStudentsByProfessorIdAndCourseId_shouldThrowExceptionTest1(){
		Integer courseId = 7;
		Integer professorId = 4;
		assertThrows(ProfessorNotExistsInSystemException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}
	@Test
	public void viewRegisteredStudentsByProfessorIdAndCourseId_shouldThrowExceptionTest2(){
		Integer courseId = 17;
		Integer professorId = 1006;
		assertThrows(ProfessorNotAssignedForCourseException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}
}
