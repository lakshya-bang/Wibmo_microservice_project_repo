package com.wibmo.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;
import com.wibmo.dao.StudentDAO;
import com.wibmo.dao.StudentDAOImpl;

public class StudentOperationImpl implements StudentOperation {

	private final StudentDAO studentDAO;
	
	public StudentOperationImpl() {
		studentDAO = new StudentDAOImpl();
	}
	
	@Override
	public Student getStudentById(Long studentId) {
		return studentOperationDAO.getStudentById(studentId);
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
	public boolean registerCourse(Long studentId, int courseId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReportCard getReportCard(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
