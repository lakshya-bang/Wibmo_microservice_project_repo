package com.wibmo.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;
import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.dao.StudentDAO;

public class StudentOperationImpl implements StudentOperation {

	private final StudentDAO studentDAO;
	
	public StudentOperationImpl() {
		studentDAO = StudentDAOImpl.getInstance();
	}
	
//	CourseRegistrationOperation courseRegistration = new CourseRegistrationOperationImpl(null,null);
//	CourseOperationImpl courseOperation = new CourseOperationImpl();
//	Student student = new Student();
	
	@Override
	public Student getStudentById(Long studentId) {
		return studentDAO.getStudentById(studentId);
	}

	@Override
	public boolean registerCourses(Long studentId, Set<Long> courseIds) {
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
	public boolean addCourse(int courseId, Long studentId) {
		// TODO Auto-generated method stub
		studentDAO.addCourse(courseId, studentId);
		return true;
	}

	@Override
	public boolean dropCourse(int courseId, Long studentId) {
		// TODO Auto-generated method stub
		studentDAO.dropCourse(courseId, studentId);
		return false;
	}

	@Override
	public BillDueNotification payBill(int billId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> viewRegisteredCourses(Long studentId) {
		// TODO Auto-generated method stub
		studentDAO.viewRegisteredCourses(studentId);
		return null;
	}

	@Override
	public boolean registerCourse(Long studentId, int courseId) {
		return studentDAO.registerCourse(studentId, null);
	}

	@Override
	public ReportCard getReportCard(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
