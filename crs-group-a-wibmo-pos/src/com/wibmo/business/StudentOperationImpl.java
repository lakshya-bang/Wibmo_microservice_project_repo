package com.wibmo.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;
import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.dao.StudentDAO;
import com.wibmo.exception.CoursesNotAvailableForRegistrationException;
import com.wibmo.exception.StudentAlreadyRegisteredForSemesterException;
import com.wibmo.exception.StudentNotFoundException;

public class StudentOperationImpl implements StudentOperation {
	
	AuthenticationService fakeAuthenticationService = new FakeAuthenticationService();
//	CourseRegistrationOperation courseRegistration = new CourseRegistrationOperationImpl(null,null);
//	CourseOperationImpl courseOperation = new CourseOperationImpl();
	StudentDAO studentDAO = new StudentDAOImpl();
	Student student = new Student();
	@Override
	public boolean authenticate() {
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean login(Long userId,String password) {
		return true;
		// TODO Auto-generated method stub
	}

	@Override
	public boolean registerCourse(Long studentId,int courseId) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public ReportCard getReportCard(Long studentId) {
		// TODO Auto-generated method stub
		ReportCard reportCard = new ReportCard();
		studentDAO.getGrade(studentId);
//		reportCard.setGrades();
		return reportCard;
	}

	@Override
	public boolean addCourse(int courseId) {
		// TODO Auto-generated method stub
		studentDAO.addCourse(courseId, student.getId());
		return true;
	}

	@Override
	public boolean dropCourse(int courseId) {
		// TODO Auto-generated method stub
		studentDAO.dropCourse(courseId, student.getId());
		return false;
	}

	@Override
	public BillDueNotification payBill(int billId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> viewRegisteredCourses() {
		// TODO Auto-generated method stub
		studentDAO.viewRegisteredCourses(student.getId());
		return null;
	}

	@Override
	public Optional<Student> getStudentById(Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
