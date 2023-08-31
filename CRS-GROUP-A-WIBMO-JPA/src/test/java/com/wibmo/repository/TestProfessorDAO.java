package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.entity.Professor;
import com.wibmo.service.ProfessorServiceImpl;

/**
 * @author abhishek.sharma
 */
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
	
	@Test
	void getProfessorById_whenProfessorNotFound_shouldReturnNullTest() {
		
		Optional<Professor> professorOptional = Optional.empty();
		Integer professorId = 2000;
		
		when(professorRepository.findByProfessorId(professorId))
			.thenReturn(professorOptional);
		
		Professor professor = professorService.getProfessorById(professorId);
		
		assertEquals(null, professor);
	}
	
	@Test
	void getProfessorById_whenProfessorFound_shouldReturnProfessorTest() {
		
		Optional<Professor> dummyProfessorOptional = Optional.of(
				new Professor(
						2000,						// professorId
						new Random().nextInt(),		// userId
						"hota@prof.wibmo.com",		// professorEmail
						"Prof. Chittaranjan Hota",	// professorName
						"CSE"));					// department
		
		when(professorRepository.findByProfessorId(any(Integer.class)))
			.thenReturn(dummyProfessorOptional);
		
		Professor professor = professorService.getProfessorById(2000);
		
		assertNotNull(professor);
		assertEquals(dummyProfessorOptional.get(), professor);
		
	}
	
	@Test
	void getProfessorIdToProfessorMap_whenProfessorNotFound_shouldReturnEmptyMapTest() {
		
		List<Professor> dummyProfessors = new ArrayList<>();
		
		Set<Integer> dummyProfessorIds = Set.of(2000, 20001, 20002);
		
		when(professorRepository.findAllByProfessorIdIn(any(Set.class)))
			.thenReturn(dummyProfessors);
		
		Map<Integer, Professor> professorIdToProfessorMap = professorService.getProfessorIdToProfessorMap(dummyProfessorIds);
		
		assertNotNull(professorIdToProfessorMap);
		assertTrue(professorIdToProfessorMap.isEmpty());
	}
	
	@Test
	void getProfessorIdToProfessorMap_whenSomeProfessorsFound_shouldReturnOnlyExistingProfessorsMapTest() {
		
		List<Professor> dummyProfessors = List.of(
				new Professor(
						2000,						// professorId
						new Random().nextInt(),		// userId
						"hota@prof.wibmo.com",		// professorEmail
						"Prof. Chittaranjan Hota",	// professorName
						"CSE"),
				new Professor(
						2001,
						new Random().nextInt(),
						"niku@prof.wibmo.com",
						"Dr. Nikumani Chaudhary",
						"CSE"));
		
		Set<Integer> dummyProfessorIds = Set.of(2000, 2001, 2002);
		
		when(professorRepository.findAllByProfessorIdIn(any(Set.class)))
			.thenReturn(dummyProfessors);
		
		Map<Integer, Professor> professorIdToProfessorMap = professorService.getProfessorIdToProfessorMap(dummyProfessorIds);
		
		assertNotNull(professorIdToProfessorMap);
		assertEquals(2, professorIdToProfessorMap.size());
		dummyProfessors
			.forEach(dummyProfessor -> dummyProfessor.equals(
					professorIdToProfessorMap.get(dummyProfessor.getProfessorId())));
	}
	
	@Test
	void getProfessorIdToProfessorMap_whenAllProfessorsFound_shouldReturnCompleteMapTest() {
		
		List<Professor> dummyProfessors = List.of(
				new Professor(
						2000,						// professorId
						new Random().nextInt(),		// userId
						"hota@prof.wibmo.com",		// professorEmail
						"Prof. Chittaranjan Hota",	// professorName
						"CSE"),
				new Professor(
						2001,
						new Random().nextInt(),
						"niku@prof.wibmo.com",
						"Dr. Nikumani Chaudhary",
						"CSE"),
				new Professor(
						2002,
						new Random().nextInt(),
						"geeta@prof.wibmo.com",
						"Prof. G. Geeta Kumari",
						"CSE"));
		
		Set<Integer> dummyProfessorIds = Set.of(2000, 2001, 2002);
		
		when(professorRepository.findAllByProfessorIdIn(any(Set.class)))
			.thenReturn(dummyProfessors);
		
		Map<Integer, Professor> professorIdToProfessorMap = professorService.getProfessorIdToProfessorMap(dummyProfessorIds);
		
		assertNotNull(professorIdToProfessorMap);
		assertEquals(3, professorIdToProfessorMap.size());
		dummyProfessors
			.forEach(dummyProfessor -> dummyProfessor.equals(
					professorIdToProfessorMap.get(dummyProfessor.getProfessorId())));
	}
	
	@Test
	void getAllProfessors_whenNoData_thenReturnEmptyListTest() {
		
		List<Professor> dummyProfessors = Collections.emptyList();
		
		when(professorRepository.findAll())
			.thenReturn(dummyProfessors);
		
		List<Professor> professors = professorService.getAllProfessors();
		
		assertNotNull(professors);
		assertTrue(professors.isEmpty());
		
	}
	
	@Test
	void getAllProfessors_whenDataExists_thenReturnFullListTest() {
		
		List<Professor> dummyProfessors = List.of(
				new Professor(
						2000,						// professorId
						new Random().nextInt(),		// userId
						"hota@prof.wibmo.com",		// professorEmail
						"Prof. Chittaranjan Hota",	// professorName
						"CSE"),
				new Professor(
						2001,
						new Random().nextInt(),
						"niku@prof.wibmo.com",
						"Dr. Nikumani Chaudhary",
						"CSE"),
				new Professor(
						2002,
						new Random().nextInt(),
						"geeta@prof.wibmo.com",
						"Prof. G. Geeta Kumari",
						"CSE"));
		
		when(professorRepository.findAll())
			.thenReturn(dummyProfessors);
		
		List<Professor> professors = professorService.getAllProfessors();
		
		assertNotNull(professors);
		assertEquals(3, professors.size());	
	}
	
	@Test
	void add_whenInputNull_thenDoNothingTest() {
		
		Professor dummyProfessor = null;
		
		professorService.add(dummyProfessor);
		
		verify(professorRepository, times(0))
			.save(any(Professor.class));
		
	}
	
	@Test
	void add_whenInputValid_thenShouldSaveEntityTest() {
		
		Professor dummyProfessor = 
				new Professor(
					2000,						// professorId
					new Random().nextInt(),		// userId
					"hota@prof.wibmo.com",		// professorEmail
					"Prof. Chittaranjan Hota",	// professorName
					"CSE");
		
		professorService.add(dummyProfessor);
		
		verify(professorRepository, times(1))
			.save(any(Professor.class));
		
	}
	
	@Test
	void isProfessorExistsById_whenNotFound_thenReturnFalseTest() {
		
		Boolean expectedValue = Boolean.FALSE;
		
		when(professorRepository.existsByProfessorId(any(Integer.class)))
			.thenReturn(expectedValue);
		
		Boolean actualValue = professorService.isProfessorExistsById(new Random().nextInt());
		
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	void isProfessorExistsById_whenFound_thenReturnTrueTest() {
		
		Boolean expectedValue = Boolean.TRUE;
		
		when(professorRepository.existsByProfessorId(any(Integer.class)))
			.thenReturn(expectedValue);
		
		Boolean actualValue = professorService.isProfessorExistsById(new Random().nextInt());
		
		assertEquals(expectedValue, actualValue);
	}
}
