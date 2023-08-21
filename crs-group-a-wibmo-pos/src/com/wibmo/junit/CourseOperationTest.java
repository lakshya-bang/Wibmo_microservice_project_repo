package com.wibmo.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.enums.CourseType;

/**
 * @author abhishek.sharma
 */
public class CourseOperationTest {

	private Integer courseId;
	private Integer professorId;
	private CourseOperation courseOperation;

	@Before
	public void setUp() throws Exception {
		courseId = 16;
		professorId = 1002;
		courseOperation = new CourseOperationImpl(
				new ProfessorOperationImpl());
	}

	@After
	public void tearDown() throws Exception {
		
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
		
		List<Course> courses = courseOperation
				.getCoursesAssignedToProfessor(professorId);
		
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
		
		CourseType actualCourseType = courseOperation
					.getCourseTypeByCourseId(courseId);
		
		assertEquals(expectedCourseType, actualCourseType);
	}
	
	@Test
	public void getCourseTypeByCourseId_primaryCourseTypeTest() {
		
		courseId = 16;
		
		CourseType expectedCourseType = CourseType.PRIMARY;
		
		CourseType actualCourseType = courseOperation
					.getCourseTypeByCourseId(courseId);
		
		assertEquals(expectedCourseType, actualCourseType);
	}
	
	
}
