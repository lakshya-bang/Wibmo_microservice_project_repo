/**
 * 
 */
package com.wibmo.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wibmo.application.CrsGroupAWibmoRestApplication;
import com.wibmo.dao.CourseRegistrationDAOImpl;
import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.ProfessorNotAssignedForCourseException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllAlternativeCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForAllPrimaryCoursesException;
import com.wibmo.exception.StudentAlreadyRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotRegisteredForCourseInSemesterException;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.CourseService;
import com.wibmo.service.CourseServiceImpl;
import com.wibmo.service.CourseRegistrationService;
import com.wibmo.service.CourseRegistrationServiceImpl;
import com.wibmo.service.ProfessorService;
import com.wibmo.service.ProfessorServiceImpl;
import com.wibmo.service.StudentService;
import com.wibmo.service.StudentServiceImpl;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class CourseRegistrationOperationTest {
	
	@Autowired
	private CourseRegistrationServiceImpl courseRegistrartionOperation;
	@Autowired
	private CourseRegistrationDAOImpl courseRegDAO;

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
		Student testStudent = new Student(1012, "paras@stu.user.com", "PARAS", 1);
		
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.viewRegisteredCoursesByStudent(testStudent));
	}
	
	/**
	 * junit test for getRegistrationStatusByStudent
	 */
	@Test
	public void getRegistrationStatusByStudent_shouldThrowExceptionTest() {
		Student testStudent = new Student(1012, "paras@stu.user.com", "PARAS", 1);
		
		assertThrows(StudentNotRegisteredForSemesterException.class,
				()->courseRegistrartionOperation.getRegistrationStatusByStudent(testStudent));
	}
	
	@Test
	public void getRegistrationStatusByStudentTest() {
		Student testSt = new Student(1008, "ayush@stu.user.com", "Aayush", 1);
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
		testStudent.setStudentId(1012);
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
	@Test
	public void addCourseTest(){
		Integer courseId = 11;
		Student testStudent = new Student(1017, "robert@stu.user.com", "Robert", 1);
		boolean expectedOutput = false;
		try {
			courseRegistrartionOperation.addCourse(courseId, testStudent);
			expectedOutput = true;

		} catch (StudentNotRegisteredForSemesterException | StudentAlreadyRegisteredForCourseInSemesterException
				| StudentAlreadyRegisteredForAllAlternativeCoursesException
				| StudentAlreadyRegisteredForAllPrimaryCoursesException | CourseNotExistsInCatalogException e) {
			e.printStackTrace();
		}
		boolean actualOutput = courseRegDAO.existsByStudentAndCourseId(testStudent, courseId);
		assertEquals(expectedOutput, actualOutput);
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
	@Test
	public void dropCourseTest(){
		Integer courseId = 11;
		Student testStudent = new Student(1017, "robert@stu.user.com", "Robert", 1);
		boolean expectedOutput = true;
		
		try {
			courseRegistrartionOperation.dropCourse(courseId, testStudent);
			expectedOutput = false;
		} catch (CourseNotExistsInCatalogException | StudentNotRegisteredForSemesterException
				| StudentNotRegisteredForCourseInSemesterException e) {
			e.printStackTrace();
		}
//		boolean actualOutput = courseRegistrartionOperation.isStudentRegisteredForCourse(testStudent, courseId);
		
		boolean actualOutput = courseRegDAO.existsByStudentAndCourseId(testStudent, courseId);
		
		assertEquals(expectedOutput, actualOutput);
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
	@Test
	public void getRegisteredStudentsByCourseIdTest(){
		Integer courseId = 2;
		List<Student> expectedstudent = new ArrayList<>();
		Student st = new Student(1001, "abhi@stu.user.com", "Abhishek", 1);
		expectedstudent.add(st);
		st = new Student(1008, "ayush@stu.user.com", "Ayush", 1);
		expectedstudent.add(st);
		st = new Student(1009, "arpit@stu.user.com", "Arpit", 1);
		expectedstudent.add(st);
		st = new Student(1010, "mohit@stu.user.com", "Mohit", 1);
		expectedstudent.add(st);
		
		List<Student> actualStudent = null;
		try {
			actualStudent = courseRegistrartionOperation.getRegisteredStudentsByCourseId(courseId);
		} catch (CourseNotExistsInCatalogException e) {
			e.printStackTrace();
		}
		assertEquals(new HashSet<Student>(expectedstudent), new HashSet<Student>(actualStudent));
	}
	
	/**
	 * junit test for getCourseIdToRegisteredStudentsMappingByProfessorId
	 */
	//TODO: have to to debugging error only one student mapping
	@Test
	public void getCourseIdToRegisteredStudentsMappingByProfessorIdTest(){
		Integer professorId = 1003;
		
		Integer courseId = 3;
		List<Student> student = new ArrayList<>();
		Student st = new Student(1008, "ayush@stu.user.com", "Ayush", 1);
		student.add(st);
		st = new Student(1017, "robert@stu.user.com", "Robert", 1);
		student.add(st);
		
		Map<Integer, List<Student>> expectedMap = new HashMap<>();
		expectedMap.put(courseId, student);
		
		Map<Integer, List<Student>> actualMap = new HashMap<>();
		try {
			actualMap = courseRegistrartionOperation.getCourseIdToRegisteredStudentsMappingByProfessorId(professorId);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expectedMap, actualMap);
	}
		
	/**
	 * junit tests for viewRegisteredStudentsByProfessorIdAndCourseId
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
		assertThrows(UserNotFoundException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}
	@Test
	public void viewRegisteredStudentsByProfessorIdAndCourseId_shouldThrowExceptionTest2(){
		Integer courseId = 17;
		Integer professorId = 1102;
		assertThrows(ProfessorNotAssignedForCourseException.class,
				()->courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId,courseId));
	
	}
	@Test
	public void viewRegisteredStudentsByProfessorIdAndCourseIdTest() {
		Integer professorId = 1007;
		Integer courseId = 11;
		List<Student> expectedstudent = new ArrayList<>();
		Student st = new Student(1001, "abhi@stu.user.com", "Abhishek", 1);
		expectedstudent.add(st);
		st = new Student(1010, "mohit@stu.user.com", "Mohit", 1);
		expectedstudent.add(st);
		List<Student> actualStudent = null;
		
		try {
			courseRegistrartionOperation.viewRegisteredStudentsByProfessorIdAndCourseId(professorId, courseId);
			actualStudent = courseRegistrartionOperation.getRegisteredStudentsByCourseId(courseId);
		} catch (CourseNotExistsInCatalogException | UserNotFoundException | ProfessorNotAssignedForCourseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(new HashSet<Student>(expectedstudent), new HashSet<Student>(actualStudent));

	}	
	
	/**
	 * Junit test for updateCourseRegistrationStatusToByRegistrationIds
	 */
	@Test
	public void updateCourseRegistrationStatusToByRegistrationIdsTest() {
		Set<Integer> courseRegistrationIds = new HashSet<>();
		courseRegistrationIds.add(4);
		courseRegistrationIds.add(5);
		boolean expectedResult = true;
		
		boolean actualResult = courseRegistrartionOperation.updateCourseRegistrationStatusToByRegistrationIds(RegistrationStatus.APPROVED, courseRegistrationIds);
		
		assertEquals(expectedResult, actualResult);
	}
	
	
	/**
	 * junit test for updateAllPendingCourseRegistrationsTo
	 */
	@Test
	public void updateAllPendingCourseRegistrationsToTest(){
		boolean expectedValue = true;
		boolean actualValue = courseRegistrartionOperation.updateAllPendingCourseRegistrationsTo(RegistrationStatus.APPROVED);
		assertEquals(expectedValue, actualValue);
	}
	
}
