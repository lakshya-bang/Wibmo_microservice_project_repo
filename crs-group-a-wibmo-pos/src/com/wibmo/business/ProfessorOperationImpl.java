package com.wibmo.business;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.bean.Student;
import com.wibmo.bean.Professor;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.dao.ProfessorDAOImpl;


public class ProfessorOperationImpl implements ProfessorOperation {

	ProfessorDAO professorDAO = new ProfessorDAOImpl();
	
	@Override
	public Professor getProfessorById(Integer userId) {
		return professorDAO.findById(userId);
	}

	@Override
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds) {
		return professorDAO
				.findAllByIdIn(professorIds)
				.stream()
				.collect(Collectors.toMap(
						Professor::getProfessorId, 
						Function.identity()));
	}

	@Override
	public Map<Integer, List<Student>> getCourseIdToEnrolledStudentsMapping(Integer professorId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
