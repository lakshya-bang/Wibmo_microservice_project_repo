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

	StudentDAO studentDAO = new StudentDAOImpl();
	
	@Override
	public Student getStudentById(Integer studentId) {
		return studentDAO.findById(studentId);
	}
	
}
