/**
 * 
 */
package com.wibmo.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Student;
import com.wibmo.business.AuthenticationService;
import com.wibmo.business.CourseOperation;
import com.wibmo.business.CourseOperationImpl;
import com.wibmo.business.CourseRegistrationOperation;
import com.wibmo.business.CourseRegistrationOperationImpl;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;
import com.wibmo.business.StudentOperation;
import com.wibmo.business.StudentOperationImpl;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.RegistrationStatus;
/**
 * 
 */
public class CourseRegistrationOperationTest {

	private CourseRegistrationOperation courseRegistrartionOperation;
	private StudentOperation studentOperation;
	private ProfessorOperation professorOperation;
	private CourseOperation courseOperation;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		studentOperation = new StudentOperationImpl();
		professorOperation = new ProfessorOperationImpl();
		courseOperation = new CourseOperationImpl(professorOperation);
		courseRegistrartionOperation = new CourseRegistrationOperationImpl(studentOperation, professorOperation, courseOperation);
	}
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
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	

}
