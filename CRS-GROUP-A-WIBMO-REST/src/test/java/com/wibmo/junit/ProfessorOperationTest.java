/**
 * 
 */
package com.wibmo.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wibmo.application.CrsGroupAWibmoRestApplication;
import com.wibmo.bean.Professor;
import com.wibmo.service.ProfessorService;
import com.wibmo.service.ProfessorServiceImpl;
import com.wibmo.service.UserServiceImpl;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class ProfessorOperationTest {
	private Integer professorId = 1002;
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
	@Autowired
	private ProfessorServiceImpl professorOperation;
	@Autowired
	private UserServiceImpl userOperation;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
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
		assertTrue(userOperation.isUserExistsById(professorId));
	}
	
	@Test
	public void isProfessorExistByIdTestFail() {
		assertFalse(userOperation.isUserExistsById(131));
	}
	
	@Test
	public void addTest() {
		
		Professor professor = new Professor(
				1102,
				"test2@test.com",
				"test2",
				"Test2");
		
		String expectedOutput = "Account Registration sent to Admin for Approval.";
		
		professorOperation.add(professor);
		
		assertEquals(
				expectedOutput,
				outputStreamCaptor.toString().trim());
	}

}
