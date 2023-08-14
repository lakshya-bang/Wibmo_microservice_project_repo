package com.wibmo.business;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.dao.ProfessorDAOImpl;

public interface ProfessorBusiness {
	public static Professor fetchDetails(User user) {
		Professor professor = new Professor();
		professor.setProfessorName(user.getUserName());
		professor.setProfessorID(user.getUserId());
		ProfessorDAOImpl professorDao = ProfessorDAOImpl.getInstance();
		professor.setCoursesTaught(professorDao.fetchCoursesTaught(user.getUserId(), 1, 1));
		return professor;
	}
	void viewEnrolledStudents(Professor professor);
	
	Boolean updateGradeDetails();
}
