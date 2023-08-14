package com.wibmo.dao;

import java.util.List;

import com.wibmo.bean.Course;
import com.wibmo.bean.ReportCard;
import com.wibmo.bean.Student;

public class StudentDAOImpl implements StudentDAO {
	
	@Override
	public ReportCard getReportCard(int StudentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCourse(int courseId, int StudentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dropCourse(int courseId, int StudentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean payBill(float billId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Course> viewRegisteredCourses(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
