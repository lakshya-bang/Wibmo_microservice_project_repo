package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
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

import com.wibmo.entity.Student;
import com.wibmo.enums.UserType;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.StudentServiceImpl;

/**
 * @author manali.kumari
 */

@ExtendWith(MockitoExtension.class)
public class TestStudentDAO {

	@InjectMocks
	StudentServiceImpl studentService;
	
	@Mock
	private StudentRepository studentRepository;
	
	@Test
	public void getAllStudentTest() {
		List<Student> expectedStudent = List.of(
				new Student(1, 1001, "abc@gmail.com", "abc", 1),
				new Student(2, 1002, "xyz@gmail.com", "xyz", 1));
		
		when(studentRepository.findAll())
		.thenReturn(expectedStudent);
		
		List<Student> actualStudent = studentService.getAllStudents();
		
		assertNotNull(actualStudent);
		assertEquals(expectedStudent.size(), actualStudent.size());
		assertTrue(expectedStudent.equals(actualStudent));
	}
	
	@Test
	void getAllStudents_studentEmptyList_shouldReturnEmptyListTest() {	
		List<Student> expectedStudent = Collections.emptyList();
		
		when(studentRepository.findAll()).thenReturn(expectedStudent);
		
		List<Student> actualStudent = studentService.getAllStudents();
		
		assertNotNull(actualStudent);
		assertTrue(actualStudent.isEmpty());
		
	}
	
	//TODO: ids more than list
	@Test
	public void findAllByStudentIdTest() {
		List<Student> expectedStudent = List.of(
				new Student(1, 1001, "abc@gmail.com", "abc", 1),
				new Student(2, 1002, "xyz@gmail.com", "xyz", 1),
				new Student(3, 1003, "bob@gmail.com", "xyz", 1),
				new Student(4, 1004, "alice@gmail.com", "xyz", 1));
		when(studentRepository.findAllByStudentIdIn(any(List.class))).thenReturn(expectedStudent);
		
		Collection<Integer> Ids = List.of(1,2,3,4);
		List<Student> actualStudent = studentService.getAllStudentsByIds(Ids);
		
		assertNotNull(actualStudent);
		assertEquals(expectedStudent.size(), actualStudent.size());
		assertTrue(expectedStudent.equals(actualStudent));
	}
	
	@Test
	public void findAllByStudentId_shouldreturnExistingStudentTest() {
		List<Student> expectedStudent = List.of(
				new Student(1, 1001, "abc@gmail.com", "abc", 1),
				new Student(2, 1002, "xyz@gmail.com", "xyz", 1));
		
		
		when(studentRepository.findAllByStudentIdIn(any(List.class))).thenReturn(expectedStudent);
		when(studentRepository.existsByStudentId(1)).thenReturn(true);
		when(studentRepository.existsByStudentId(2)).thenReturn(true);
		
		Collection<Integer> Ids = List.of(1,2,3,4,5,6);
		List<Student> actualStudent = studentService.getAllStudentsByIds(Ids);
		
		assertNotNull(actualStudent);
		assertEquals(expectedStudent.size(), actualStudent.size());
		assertTrue(expectedStudent.equals(actualStudent));
	}
	
	@Test
	public void findByStudentIdTest() throws UserNotFoundException {
		Optional<Student> expectedStudent = Optional.of(new Student(1, 1001, "abc@gmail.com", "abc", 1));
		
		when(studentRepository.findByStudentId(1)).thenReturn(expectedStudent);
		
		Student actualStudent = studentService.getStudentById(1);
		
		assertNotNull(actualStudent);
		assertEquals(expectedStudent.get(), actualStudent);
	}
	
	
	@Test
	void findByStudentId__shouldThrowUserNotFoundExceptionTest() {
		Exception exception = assertThrows(UserNotFoundException.class, () -> {
			studentService.getStudentById(2000);
	    });

	    String expectedMessage = UserType.STUDENT.toString() + " with Id = " + 2000 + " NOT Found.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	
	@Test
	public void getStudentIdToStudentMapTest() {
		List<Student> studentList = List.of(
				new Student(1, 1001, "abc@gmail.com", "abc", 1),
				new Student(2, 1002, "xyz@gmail.com", "xyz", 1));
		
		when(studentRepository.findAllByStudentIdIn(any(List.class))).thenReturn(studentList);
		
		Map<Integer, Student> expectedMap = Map.of(1, studentList.get(0),
				2, studentList.get(1));
		
		Collection<Integer> ids = List.of(1,2);
		Map<Integer, Student> actualMap = studentService.getStudentIdToStudentMap(ids);
		
		assertNotNull(actualMap);
		assertEquals(expectedMap.size(), actualMap.size());
		assertEquals(expectedMap, actualMap);
	}
	
	@Test
	void ggetStudentIdToStudentMap_shouldReturnOnlyExistingStudentMapTest() {
		
		List<Student> studentList = List.of(
				new Student(1, 1001, "abc@gmail.com", "abc", 1),
				new Student(2, 1002, "xyz@gmail.com", "xyz", 1));
				
		when(studentRepository.findAllByStudentIdIn(any(List.class))).thenReturn(studentList);
		Map<Integer, Student> expectedMap = Map.of(1, studentList.get(0),
				2, studentList.get(1));
		
		Collection<Integer> ids = List.of(1,2,3,4);
		Map<Integer, Student> actualMap = studentService.getStudentIdToStudentMap(ids);

		assertNotNull(actualMap);
		assertEquals(2, actualMap.size());
		assertEquals(expectedMap, actualMap);

	}
	
	//error
	@Test
	public void getCurrentSemesterByStudentIdTest() throws UserNotFoundException {
		Optional<Student> student = Optional.of(new Student(1, 1001, "abc@gmail.com", "abc", 2));
		
		Integer expectedRes = 2;
		when(studentRepository.existsByStudentId(any(Integer.class))).thenReturn(true);
		
		when(studentRepository.findByStudentId(1)).thenReturn(student);
		Integer actualRes = studentService.getCurrentSemesterByStudentId(1);
		
		assertEquals(expectedRes, actualRes);
	}

	@Test
	public void getCurrentSemesterByStudentId_shouldTrowUserNotFoundExceptionTest(){
		Exception exception = assertThrows(UserNotFoundException.class, () -> {
			studentService.getCurrentSemesterByStudentId(2000);
	    });

	    String expectedMessage = UserType.STUDENT.toString() + " with Id = " + 2000 + " NOT Found.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test 
	public void isExistsByStudentIdTest(){
		Boolean expectedResult = true;
		when(studentRepository.existsByStudentId(1)).thenReturn(expectedResult);
		
		Boolean actualResult = studentService.isStudentExistsById(1);
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void isExistsByStudentId_shouldReturnFalseTest() {
		
		Boolean expectedResult = false;
		
		when(studentRepository.existsByStudentId(1)).thenReturn(expectedResult);
		
		Boolean actualResult = studentService.isStudentExistsById(1);
		assertEquals(expectedResult, actualResult);
	}
	
	
	@Test
    public void add_nullInputTest() {

		Student student = null;

        studentService.add(student);

        verify(studentRepository,times(0)).save(student);

    }
	
	@Test
	void add_whenInputValid_thenShouldSaveEntityTest() {
		
		Student student = new Student(1, 101, "abc@gmail.com", "abc", 1);
		
		studentService.add(student);
		
		verify(studentRepository, times(1))
			.save(student);
	}
	
}
