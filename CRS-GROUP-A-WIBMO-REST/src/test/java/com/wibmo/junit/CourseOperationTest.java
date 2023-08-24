/**
 * 
 */
package com.wibmo.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wibmo.application.CrsGroupAWibmoRestApplication;
import com.wibmo.bean.Course;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.CourseService;
import com.wibmo.service.CourseServiceImpl;
import com.wibmo.service.ProfessorServiceImpl;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class CourseOperationTest {

	private Integer courseId;
	private Integer professorId;
	
	@Autowired
	private CourseServiceImpl courseOperation;


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		courseId = 16;
		professorId = 1002;
//		courseOperation = new CourseOperationImpl(
//				new ProfessorOperationImpl());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void getCourseIdToCourseMapTest1() {
		
		Course expectedCourse = new Course(
				courseId, 
				"C Programming Lang.", 
				1, 
				2021, 
				"CSE",
				1002, 
				Boolean.FALSE, 
				10,
				CourseType.PRIMARY);
		
		Course actualCourse = courseOperation
				.getCourseIdToCourseMap(Set.of(courseId))
				.get(courseId);
		
		assertEquals(expectedCourse, actualCourse);
	}
	
	@Test
	public void getCourseIdToCourseMapTest2() {
		
		Course expectedCourse = new Course(
				courseId, 
				"C Programming Lang.", 
				1, 
				2021, 
				"ECE",	// instead of CSE
				1002, 
				Boolean.FALSE, 
				10,
				CourseType.PRIMARY);
		
		Course actualCourse = courseOperation
				.getCourseIdToCourseMap(Set.of(courseId))
				.get(courseId);
		
		assertNotEquals(expectedCourse, actualCourse);
	}

	@Test
	public void getCoursesAssignedToProfessorTest() {
		
		List<Course> courses = null;
		try {
			courses = courseOperation
					.getCoursesAssignedToProfessor(professorId);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		
		assertNotNull(courseOperation);
		assertEquals(3, courses.size());
		assertEquals(2, courses
						.stream()
						.map(course -> course.getCourseType())
						.filter(courseType -> courseType.equals(
								CourseType.PRIMARY))
						.count());
		
	}
	
	@Test
	public void getCourseTypeByCourseId_alternativeCourseTypeTest() {
		
		courseId = 14;
		
		CourseType expectedCourseType = CourseType.ALTERNATIVE;
		
		CourseType actualCourseType = null;
		try {
			actualCourseType = courseOperation
						.getCourseTypeByCourseId(courseId);
		} catch (CourseNotExistsInCatalogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expectedCourseType, actualCourseType);
	}
	
	@Test
	public void getCourseTypeByCourseId_primaryCourseTypeTest() {
		
		courseId = 16;
		
		CourseType expectedCourseType = CourseType.PRIMARY;
		
		CourseType actualCourseType = null;
		try {
			actualCourseType = courseOperation
						.getCourseTypeByCourseId(courseId);
		} catch (CourseNotExistsInCatalogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expectedCourseType, actualCourseType);
	}


}
