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
	
	@Override
	public void add(Professor professor) {
		
		if(null == professor) {
			return;
		}
		
		professorRepository.save(professor);
	}
	

}
