package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.entity.Professor;
import com.wibmo.repository.ProfessorRepository;

/**
 * 
 */
@Service
public class ProfessorServiceImpl implements ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;
	
	/**
	 * @param professorId (Integer)
	 * @return Professor
	 */
	@Override
	public Professor getProfessorById(Integer professorId) {
		Optional<Professor> professorOptional = professorRepository.findById(professorId);
		return professorOptional.isPresent()
				? professorOptional.get()
				: null;
	}
	
	/**
	 * @param professorIds (Integer Set)
	 * @return Map<Integer,Professor>
	 */
	@Override
	public Map<Integer, Professor> getProfessorIdToProfessorMap(Collection<Integer> professorIds) {
		return professorRepository
				.findAllByProfessorIdIn(professorIds)
				.stream()
				.collect(Collectors.toMap(
						Professor::getProfessorId, 
						Function.identity()));
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public List<Professor> getAllProfessors() {
		return professorRepository.findAll();
	}
	
	/**
	 * @param Professor
	 */
	@Override
	public void add(Professor professor) {
		
		if(null == professor) {
			return;
		}
		
		professorRepository.save(professor);
	}
	
	@Override
	public Boolean isProfessorExistsById(Integer professorId) {
		return professorRepository.existsByProfessorId(professorId);
	}
}
