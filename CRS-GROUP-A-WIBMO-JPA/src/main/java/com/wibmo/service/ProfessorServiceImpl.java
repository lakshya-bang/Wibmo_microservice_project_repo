package com.wibmo.service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wibmo.repository.*;
import com.wibmo.entity.Professor;

/**
 * 
 */
@Service
public class ProfessorServiceImpl implements ProfessorService {

	private ProfessorRepository professorRepository;
	
	/**
	 * @param professorId (Integer)
	 * @return Professor
	 */
	@Override
	public Professor getProfessorById(Integer professorId) {
		return professorRepository
				.findAllByIdIn(Set.of(professorId))
				.get(0);
	}
	
	/**
	 * @param professorIds (Integer Set)
	 * @return Map<Integer,Professor>
	 */

	@Override
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Set<Integer> professorIds) {
		return professorRepository
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

	/**
	 * @param Professor
	 */
	
	@Override
	public void add(Professor professor) {
		
		// TODO
//		if(!userOperation.isUserExistsById(professor.getProfessorId())) {
//			
//		}
		
		professorRepository.save(professor);
		
		System.out.println("Account Registration sent to Admin for Approval.");
	}
	
}
