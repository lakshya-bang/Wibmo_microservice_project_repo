/**
 * 
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.entity.Student;
import com.wibmo.repository.StudentRepository;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class TestStudentService {

	@InjectMocks
	StudentServiceImpl studentService;
	
	@Mock
	StudentRepository studentRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAdd_when_student_null() {
		Student student = null;
		studentService.add(student);
		verify(studentRepository, times(0))
		.save(any(Student.class));
		
	}
	
	@Test
	void testAdd_when_student_not_null() {
		Student student = new Student();
		studentService.add(student);
		verify(studentRepository, times(1))
		.save(any(Student.class));
	}

}
