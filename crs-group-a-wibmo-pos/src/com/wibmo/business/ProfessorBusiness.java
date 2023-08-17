package com.wibmo.business;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import com.wibmo.bean.Professor;
import com.wibmo.bean.Student;
import com.wibmo.bean.User;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.dao.ProfessorDAOImpl;

public interface ProfessorBusiness {
	public static Professor fetchDetails(User user) {
		Professor professor = new Professor();
		professor.setProfessorId(user.getUserId());
		return professor;
	}
	void viewEnrolledStudents(Professor professor);
	
	void updateGradeDetails(Integer studentId, Integer courseId, String grade);
}

