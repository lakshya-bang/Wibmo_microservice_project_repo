package com.wibmo.business;

import java.util.List;
import java.util.Map;

import com.wibmo.bean.Student;
import com.wibmo.bean.Professor;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.dao.ProfessorDAOImpl;


public class ProfessorOperationImpl implements ProfessorOperation {

	ProfessorDAO professorDAO = new ProfessorDAOImpl();
	
	@Override
	public Professor getProfessorById(Long userId) {
		return professorDAO.findById(userId);
	}
	
	@Override
	public Map<Long, List<Student>> getCourseIdToEnrolledStudentsMapping(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
