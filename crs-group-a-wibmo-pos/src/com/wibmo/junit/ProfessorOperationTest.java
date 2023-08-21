/**
 * 
 */
package com.wibmo.junit;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Professor;
import com.wibmo.business.ProfessorOperation;
import com.wibmo.business.ProfessorOperationImpl;


/**
 * @author 
 */
public class ProfessorOperationTest {
	
	private Integer professorId = 1002;
	private ProfessorOperation professorOperation;
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		professorOperation = new ProfessorOperationImpl();
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@Test
	public void getProfessorById() {
		Professor professor = null;
		professor = professorOperation.getProfessorById(professorId);
		assertNotNull(professor);
	}
	
	@Test
	public void getProfessorIdtoProfessorMapTest() {
		Map<Integer,Professor> professorIdtoProfessor = new HashMap<Integer,Professor>();
		professorIdtoProfessor = professorOperation.getProfessorIdToProfessorMap(Set.of(professorId));
		assertFalse(professorIdtoProfessor.isEmpty());
	}

	@Test
	public void isProfessorExistsByIdTest() {
		assertTrue(professorOperation.isProfessorExistsById(professorId));
	}
	
	@Test
	public void isProfessorExistByIdTestFail() {
		assertFalse(professorOperation.isProfessorExistsById(131));
	}
	
	@Test
	public void addTest() {
		
		Professor professor = new Professor(
				1101,
				"test@test.com",
				"test1",
				"Test");
		
		String expectedOutput = "Account Registration sent to Admin for Approval.";
		
		professorOperation.add(professor);
		
		assertEquals(
				expectedOutput,
				outputStreamCaptor.toString().trim());
	}
}
