package com.wibmo.business;

import java.util.List;
import java.util.Optional;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

public class StudentOperationImpl implements StudentOperation {

	@Override
	public boolean authenticate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerCourse(int courseId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReportCard getReportCard(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCourse(int courseId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dropCourse(int courseId) {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public Optional<Student> getStudentById(Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
