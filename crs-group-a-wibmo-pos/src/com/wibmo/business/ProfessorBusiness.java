package com.wibmo.business;

import com.wibmo.bean.Professor;
import com.wibmo.bean.User;

public interface ProfessorBusiness {
	
	public static Professor fetchDetails(User user) {
		Professor professor = new Professor();
		professor.setProfessorId(user.getUserId());
		return professor;
	}
	
	void viewEnrolledStudents(Professor professor);
	
	void updateGradeDetails();
}

