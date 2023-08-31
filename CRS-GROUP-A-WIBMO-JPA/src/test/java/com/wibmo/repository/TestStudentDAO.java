package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.entity.Student;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestStudentDAO {

	@InjectMocks
	StudentServiceImpl studentService;
	
	@Mock
	private StudentRepository studentRepository;
	
	//null case
	@Test
	public void findAllStudentTest() {
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
	
	//empty optional
	@Test
	public void findByStudentIdTest() throws UserNotFoundException {
		Optional<Student> expectedStudent = Optional.of(new Student(1, 1001, "abc@gmail.com", "abc", 1));
		
		when(studentRepository.findByStudentId(1)).thenReturn(expectedStudent);
		
		Student actualStudent = studentService.getStudentById(1);
		
		assertNotNull(actualStudent);
		assertEquals(expectedStudent.get(), actualStudent);
	}
	
	//empty optional
	@Test
	public void isStudentExistsByIdTest() throws UserNotFoundException {
		//Optional<Student> student = Optional.of(new Student(1, 1001, "abc@gmail.com", "abc", 1));
		Optional<Student> stNull = Optional.ofNullable(null);
		when(studentRepository.findByStudentId(null)).thenReturn(stNull);
		
		boolean expectedResult = false;
		boolean actualResult = studentService.isStudentExistsById(null);
		
		assertEquals(expectedResult, actualResult);
	}
	
	// not null check
	@Test
	public void getStudentIdToStudentMapTest() {
		List<Student> studentList = List.of(
				new Student(1, 1001, "abc@gmail.com", "abc", 1),
				new Student(2, 1002, "xyz@gmail.com", "xyz", 1));
		Collection<Integer> ids = List.of(1,2);
		when(studentRepository.findAllByStudentIdIn(ids)).thenReturn(studentList);
		
		Map<Integer, Student> expectedMap = Map.of(1, studentList.get(0),
				2, studentList.get(1));
		
		Map<Integer, Student> actualMap = studentService.getStudentIdToStudentMap(ids);
		
		assertNotNull(actualMap);
		assertEquals(expectedMap.size(), actualMap.size());
		assertEquals(expectedMap, actualMap);
	}
	
	@Test
	public void getCurrentSemesterByStudentIdTest() throws UserNotFoundException {
		Optional<Student> student = Optional.of(new Student(1, 1001, "abc@gmail.com", "abc", 2));
		when(studentRepository.findByStudentId(1)).thenReturn(student);
		
		Integer expectedRes = 2;
		Integer actualRes = studentService.getCurrentSemesterByStudentId(1);
		
		assertEquals(expectedRes, actualRes);
	}
	
	@Test
	public void getCurrentSemesterByStudentId_shouldTrowUserNotFoundExceptionTest(){
		when(studentRepository.findByStudentId(1)).thenReturn(null);
		
		assertThrows(
				UserNotFoundException.class, 
				() -> studentService.getCurrentSemesterByStudentId(4));
	}
	
	@Test 
	public void isExistsByStudentIdTest() {
		List<Student> studentList = List.of(new Student(1, 1001, "abc@gmail.com", "abc", 1));
	}
}
