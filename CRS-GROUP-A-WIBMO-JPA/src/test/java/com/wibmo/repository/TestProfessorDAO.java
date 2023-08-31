package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.entity.Professor;
import com.wibmo.service.ProfessorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestProfessorDAO {

	// Dependency
	@InjectMocks
	private ProfessorServiceImpl professorService;
	
	// Faking
	@Mock
	private ProfessorRepository professorRepository;
	
	@Test
	void findAllProfessors() {
		
		List<Professor> expectedProfessors = List.of(
				new Professor(
						new Random().nextInt(),
						new Random().nextInt(),
						new String(),
						new String(),
						new String()),
				new Professor(
						new Random().nextInt(),
						new Random().nextInt(),
						new String(),
						new String(),
						new String()));
		
		when(professorRepository.findAll())
			.thenReturn(expectedProfessors);
		
		List<Professor> actualProfessors = professorService.getAllProfessors();
		
		assertEquals(expectedProfessors.size(), actualProfessors.size());
		assertEquals(expectedProfessors, actualProfessors);
		
	}
}
