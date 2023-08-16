package com.wibmo.business;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.bean.Professor;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.dao.ProfessorDAOImpl;


public class ProfessorOperationImpl implements ProfessorOperation {

	ProfessorDAO professorDAO = new ProfessorDAOImpl();
	
	@Override
	public Professor getProfessorById(Integer professorId) {
		return professorDAO
				.findAllByIdIn(Set.of(professorId))
				.get(0);
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
	
}
