package com.wibmo.business;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.bean.Professor;
import com.wibmo.dao.ProfessorDAO;
import com.wibmo.dao.ProfessorDAOImpl;

/**
 * 
 */
public class ProfessorOperationImpl implements ProfessorOperation {

	ProfessorDAO professorDAO = ProfessorDAOImpl.getInstance();
	
	/**
	 * @param professorId (Integer)
	 * @return Professor
	 */
	@Override
	public Professor getProfessorById(Integer professorId) {
		return professorDAO
				.findAllByIdIn(Set.of(professorId))
				.get(0);
	}
	
	/**
	 * @param professorIds (Integer Set)
	 * @return Map<Integer,Professor>
	 */

	@Override
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds) {
		return professorDAO
				.findAllByIdIn(professorIds)
				.stream()
				.collect(Collectors.toMap(
						Professor::getProfessorId, 
						Function.identity()));
	}

	/**
	 * @param professorId (Integer)
	 * @return Boolean
	 */
	
	@Override
	public Boolean isProfessorExistsById(Integer professorId) {
		return professorDAO
				.existsById(professorId);
	}

	/**
	 * @param Professor
	 */
	
	@Override
	public void add(Professor professor) {
		
		// TODO
//		if(!userOperation.isUserExistsById(professor.getProfessorId())) {
//			
//		}
		
		professorDAO.save(professor);
		
		System.out.println("Account Registration sent to Admin for Approval.");
	}
	
}
