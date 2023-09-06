/**
 * 
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.entity.Professor;
import com.wibmo.repository.ProfessorRepository;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class TestProfessorService {
	
	@InjectMocks
	ProfessorServiceImpl professorService;
	
	@Mock
	ProfessorRepository professorRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAdd_when_professor_null() {
		Professor professor = null;
		professorService.add(professor);
		verify(professorRepository, times(0))
		.save(any(Professor.class));
		
	}
	
	@Test
	void testAdd_when_professor_not_null() {
		Professor professor = new Professor();
		professorService.add(professor);
		verify(professorRepository, times(1))
		.save(any(Professor.class));
	}

}
