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

	ProfessorDAO professorDAO = ProfessorDAOImpl.getInstance();
	


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

	@Override
	public void uploadGrades() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'uploadGrades'");
	}

	@Override
	public Professor getProfessorById(Integer userId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getProfessorById'");
	}
	
}
