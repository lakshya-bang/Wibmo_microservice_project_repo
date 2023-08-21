package com.wibmo.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.enums.CourseType;

public class CourseOperationTest {

	private Integer courseId;
	private CourseOperation courseOperation;

	@Before
	public void setUp() throws Exception {
		courseId = 16;
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
	public void getCoursesAssignedToProfessorTest1() {
		
		
	}
	
}
