package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.converter.CourseConverter;
import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;
import com.wibmo.entity.Student;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.UserType;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.CourseRegistrationRepository;
import com.wibmo.repository.CourseRepository;
import com.wibmo.repository.PaymentRepository;
import com.wibmo.repository.ProfessorRepository;
import com.wibmo.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
	
	@InjectMocks
	private StudentServiceImpl studentService;
	
	@Mock
	private StudentRepository studentRepository;
	
	@Mock
	private ProfessorRepository professorRepository;
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private CourseRegistrationRepository courseRegistrationRepository;
	
	@Mock
	private PaymentRepository paymentRepository;
	
	@Spy
	private CourseConverter courseConverter;
	
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
	
	
//	@Test
//	void findByStudentId__shouldThrowUserNotFoundExceptionTest() {
//		
//		when(studentRepository.findByStudentId(any(Integer.class)))
//			.thenReturn(Optional.empty());
//		
//		Exception exception = assertThrows(UserNotFoundException.class, () -> {
//			studentService.getStudentById(2000);
//	    });
//
//	    String expectedMessage = UserType.STUDENT.toString() + " with Id = " + 2000 + " NOT Found.";
//	    String actualMessage = exception.getMessage();
//
//	    assertTrue(expectedMessage.equals(actualMessage)); 
//	}
	
	
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
	
	/******************************* Course Methods Test *******************************/
	
	@Test
	void getCourseDetailsById_whenNotFound_thenReturnNullTest() {
		
		Integer courseId = 201;
		
		when(courseRepository.findByCourseId(any(Integer.class)))
			.thenReturn(Optional.empty());
		
		assertNull(studentService.getCourseDetailsById(201));	
	
	}
	
	@Test
	void getCourseDetailsById_whenFound_whenProfessorIdNull_thenReturnCourseReponseDTO_withNullProfessorDetailsTest() {
		
		Optional<Course> fakeCourseOptional = Optional.of(new Course(
				new Random().nextInt(),
				"Dummy Course",
				1,
				LocalDate.now().getYear(),
				"Dummy Department",
				null, 					// professorId
				Boolean.FALSE,
				new Random().nextInt(),
				CourseType.PRIMARY));
		
		Integer courseId = 201;
		
		when(courseRepository.findByCourseId(any(Integer.class)))
			.thenReturn(fakeCourseOptional);
		
		CourseResponseDTO courseResponseDTO = studentService.getCourseDetailsById(201);
		
		assertNotNull(courseResponseDTO);
		
		assertEquals(fakeCourseOptional.get().getCourseId(), courseResponseDTO.getCourseId());
		assertEquals(fakeCourseOptional.get().getCourseTitle(), courseResponseDTO.getCourseTitle());
		assertEquals(fakeCourseOptional.get().getSemester(), courseResponseDTO.getSemester());
		assertEquals(fakeCourseOptional.get().getYear(), courseResponseDTO.getYear());
		assertEquals(fakeCourseOptional.get().getDepartment(), courseResponseDTO.getDepartment());
		assertEquals(fakeCourseOptional.get().getIsCancelled(), courseResponseDTO.getIsCancelled());
		assertEquals(fakeCourseOptional.get().getNoOfSeats(), courseResponseDTO.getNoOfSeats());
		
		assertNull(courseResponseDTO.getProfessorId());
		assertNull(courseResponseDTO.getProfessorName());
		assertNull(courseResponseDTO.getProfessorEmail());
		
	}
	
	@Test
	void getCourseDetailsById_whenFound_whenProfessorIdNotNull_thenReturnCourseReponseDTO_withProfessorDetailsTest() {
		
		Integer fakeProfessorId = 2001; 
		
		Optional<Course> fakeCourseOptional = Optional.of(new Course(
				new Random().nextInt(),
				"Dummy Course",
				1,
				LocalDate.now().getYear(),
				"Dummy Department",
				fakeProfessorId,
				Boolean.FALSE,
				new Random().nextInt(),
				CourseType.PRIMARY));
		
		Optional<Professor> fakeProfessorOptional = Optional.of(new Professor(
				fakeProfessorId,
				new Random().nextInt(),
				"Dummy Professor Email",
				"Dummy Professor Name",
				"Dummy Professor Department"));
		
		when(courseRepository.findByCourseId(any(Integer.class)))
			.thenReturn(fakeCourseOptional);
		when(professorRepository.findByProfessorId(any(Integer.class)))
			.thenReturn(fakeProfessorOptional);
		
		CourseResponseDTO actualCourseResponseDTO = studentService.getCourseDetailsById(201);
		
		assertNotNull(actualCourseResponseDTO);
		
		assertEquals(fakeCourseOptional.get().getCourseId(), actualCourseResponseDTO.getCourseId());
		assertEquals(fakeCourseOptional.get().getCourseTitle(), actualCourseResponseDTO.getCourseTitle());
		assertEquals(fakeCourseOptional.get().getSemester(), actualCourseResponseDTO.getSemester());
		assertEquals(fakeCourseOptional.get().getYear(), actualCourseResponseDTO.getYear());
		assertEquals(fakeCourseOptional.get().getDepartment(), actualCourseResponseDTO.getDepartment());
		assertEquals(fakeCourseOptional.get().getIsCancelled(), actualCourseResponseDTO.getIsCancelled());
		assertEquals(fakeCourseOptional.get().getNoOfSeats(), actualCourseResponseDTO.getNoOfSeats());
		
		assertNotNull(actualCourseResponseDTO.getProfessorId());
		assertNotNull(actualCourseResponseDTO.getProfessorName());
		assertNotNull(actualCourseResponseDTO.getProfessorEmail());
		
		assertEquals(fakeProfessorOptional.get().getProfessorId(), actualCourseResponseDTO.getProfessorId());
		assertEquals(fakeProfessorOptional.get().getProfessorName(), actualCourseResponseDTO.getProfessorName());
		assertEquals(fakeProfessorOptional.get().getProfessorEmail(), actualCourseResponseDTO.getProfessorEmail());
		
	}
	
	@Test
	void decrementNumOfSeatsByCourseIdsTest() {
		
		List<Integer> fakeCourseIds = List.of(1111, 2222, 3333);
		List<Integer> numOfSeats = List.of(5, 4, 8);
		
		List<Course> fakeCourses = List.of(
				new Course(
						fakeCourseIds.get(0),
						"Dummy Course",
						1,
						LocalDate.now().getYear(),
						"Dummy Department",
						new Random().nextInt(),
						Boolean.FALSE,
						numOfSeats.get(0),
						CourseType.PRIMARY),
				new Course(
						fakeCourseIds.get(1),
						"Dummy Course",
						1,
						LocalDate.now().getYear(),
						"Dummy Department",
						new Random().nextInt(),
						Boolean.FALSE,
						numOfSeats.get(1),
						CourseType.PRIMARY),
				new Course(
						fakeCourseIds.get(2),
						"Dummy Course",
						1,
						LocalDate.now().getYear(),
						"Dummy Department",
						new Random().nextInt(),
						Boolean.FALSE,
						numOfSeats.get(2),
						CourseType.PRIMARY));
		
		when(courseRepository.findAllByCourseIdIn(any(List.class)))
			.thenReturn(fakeCourses);
		
		studentService.decrementNumOfSeatsByCourseIds(fakeCourseIds);
		
		verify(courseRepository, times(1)).saveAll(fakeCourses);
		
		for(int i = 0; i < 3; i++)
			assertEquals(numOfSeats.get(i) - 1, fakeCourses.get(i).getNoOfSeats());
		
	}
	
	/*************************** Course Registration Methods *****************************/
	
	@Test
	void register_whenStudentIdNotFound_throwUserNotFoundExceptionTest() {
		
		Integer fakeStudentId = 55555;
		
		CourseRegistrationRequestDTO fakeCourseRegistrationRequestDTO = new CourseRegistrationRequestDTO(
				fakeStudentId,
				(Integer) new Random().nextInt(),
				List.of(1, 2, 3),
				List.of(2,4));
		
		when(studentRepository.findByStudentId(any(Integer.class)))
			.thenReturn(Optional.empty());
		
		assertThrows(
				UserNotFoundException.class,
				() -> studentService.register(fakeCourseRegistrationRequestDTO));
		
	}
	
	// TODO: Add more JUnits
}
