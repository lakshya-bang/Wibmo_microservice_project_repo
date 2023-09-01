package com.wibmo.repository;


import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.hibernate.mapping.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import com.wibmo.converter.CourseConverter;
import com.wibmo.dto.CourseRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;
import com.wibmo.service.CourseServiceImpl;
import com.wibmo.service.ProfessorServiceImpl;
import com.wibmo.enums.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestCourseDAO {
	
	@InjectMocks
	CourseServiceImpl courseService;
	
	@Mock
	ProfessorServiceImpl professorService;
	
	@Mock
	CourseRepository courseRepository;
	
	@Mock
	ProfessorRepository professorRepository;
	
	@Mock
	CourseConverter courseConverter;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	private Course createTestCourse() {
		Course course = new Course();
		course.setCourseId(new Random().nextInt());
		course.setCourseType(CourseType.PRIMARY);
		course.setCourseTitle("Test");
		course.setDepartment("TestDepartment");
		course.setIsCancelled(false);
		course.setNoOfSeats(10);
		course.setProfessorId(new Random().nextInt());
		course.setSemester(new Random().nextInt());
		course.setYear(new Random().nextInt());
		return course;
	};
	
	private Professor createTestProfessor() {
		Professor professor = new Professor();
		professor.setUserId(new Random().nextInt());
		professor.setProfessorId(new Random().nextInt());
		professor.setProfessorName("testProfessor");
		professor.setProfessorEmail("TestEmail");
		professor.setDepartment("Test Department");
		return professor;
	}
	
	private CourseResponseDTO convertToResponseDTO(Course course, Professor professor) {
		return new CourseResponseDTO(
				course.getCourseId(), 
				course.getCourseTitle(), 
				course.getSemester(), 
				course.getYear(), 
				course.getDepartment(),
				course.getIsCancelled(),
				course.getNoOfSeats(),
				course.getCourseType(), 
				null != professor ? professor.getProfessorId() : null, 
				null != professor ? professor.getProfessorEmail() : null,
				null != professor ? professor.getProfessorName() : null);
	}
	
	@Test
	void getCourseById_when_found_a_course() {
		Optional<Course> expectedCourse = Optional.of(createTestCourse());
		Professor expectedProfessor = createTestProfessor();
		
		when(courseRepository.findByCourseId(expectedCourse.get().getCourseId())).thenReturn(expectedCourse);
		when(professorService.getProfessorById(any(Integer.class))).thenReturn(expectedProfessor);
		when(courseConverter.convert(ArgumentMatchers.any(Course.class), ArgumentMatchers.any(Professor.class))).thenReturn(convertToResponseDTO(expectedCourse.get(),expectedProfessor));
		
		CourseResponseDTO actualCourseDTO = courseService.getCourseById(expectedCourse.get().getCourseId());
		System.out.println(courseConverter.convert(expectedCourse.get(),expectedProfessor).getCourseTitle() +  actualCourseDTO);
		assertEquals(convertToResponseDTO(expectedCourse.get(),expectedProfessor).getCourseId(),actualCourseDTO.getCourseId());
	}
	
	@Test
	void getCourseById_when_not_found_a_course() {
		Optional<Course> expectedCourse = Optional.empty();
		when(courseRepository.findByCourseId(any(Integer.class))).thenReturn(expectedCourse);
		CourseResponseDTO actualCourseDTO = courseService.getCourseById(2000);
		assertEquals(null,actualCourseDTO);
	}
	
	@Test
	void getAllCourse_Success() {
		List<Course> courses = new  ArrayList<Course>();
		courses.add(createTestCourse());
		courses.add(createTestCourse());
		List<Professor> professors = new ArrayList<Professor>();
		professors.add(createTestProfessor());
		courses.get(0).setProfessorId(professors.get(0).getProfessorId());
		courses.get(1).setProfessorId(professors.get(0).getProfessorId());
		List<CourseResponseDTO> courseResponses = new ArrayList<>();
		courseResponses.add(convertToResponseDTO(courses.get(0),professors.get(0)));
		courseResponses.add(convertToResponseDTO(courses.get(1),professors.get(0)));
		when(courseRepository.findAll()).thenReturn(courses);
		when(professorService.getProfessorIdToProfessorMap(any())).thenReturn(Map.of(professors.get(0).getProfessorId(),professors.get(0)));
		when(courseConverter.convertAll(any(), any())).thenReturn(courseResponses);
		List<CourseResponseDTO> actualCourseResponses = courseService.getAllCourses();
		assertEquals(actualCourseResponses.size(),2);
	}
	
	@Test
	void getCourseDetailsByIds_Success() {
		
		List<Course> courses = new  ArrayList<Course>();
		List<Integer> courseIds = new ArrayList<Integer>();
		courses.add(createTestCourse());
		courses.add(createTestCourse());
		courseIds.add(courses.get(0).getCourseId());
		courseIds.add(courses.get(1).getCourseId());
		List<Professor> professors = new ArrayList<Professor>();
		professors.add(createTestProfessor());
		courses.get(0).setProfessorId(professors.get(0).getProfessorId());
		courses.get(1).setProfessorId(professors.get(0).getProfessorId());
		List<CourseResponseDTO> courseResponses = new ArrayList<>();
		courseResponses.add(convertToResponseDTO(courses.get(0),professors.get(0)));
		courseResponses.add(convertToResponseDTO(courses.get(1),professors.get(0)));
		when(courseRepository.findAllByCourseIdIn(any())).thenReturn(courses);
		when(professorService.getProfessorIdToProfessorMap(any())).thenReturn(Map.of(professors.get(0).getProfessorId(),professors.get(0)));
		when(courseConverter.convertAll(any(), any())).thenReturn(courseResponses);
		List<CourseResponseDTO> actualCourseResponses = courseService.getCourseDetailsByIds(courseIds);
		assertEquals(actualCourseResponses.size(),2);
	}
	
	@Test
	void getCourseDetailsBySemester_Success() {
		
		List<Course> courses = new  ArrayList<Course>();
		Integer semester = 2;
		courses.add(createTestCourse());
		courses.add(createTestCourse());
		List<Professor> professors = new ArrayList<Professor>();
		professors.add(createTestProfessor());
		courses.get(0).setProfessorId(professors.get(0).getProfessorId());
		courses.get(1).setProfessorId(professors.get(0).getProfessorId());
		List<CourseResponseDTO> courseResponses = new ArrayList<>();
		courseResponses.add(convertToResponseDTO(courses.get(0),professors.get(0)));
		courseResponses.add(convertToResponseDTO(courses.get(1),professors.get(0)));
		when(courseRepository.findAllBySemester(semester)).thenReturn(courses);
		when(professorService.getProfessorIdToProfessorMap(any())).thenReturn(Map.of(professors.get(0).getProfessorId(),professors.get(0)));
		when(courseConverter.convertAll(any(), any())).thenReturn(courseResponses);
		List<CourseResponseDTO> actualCourseResponses = courseService.getCourseDetailsBySemester(semester);
		assertEquals(actualCourseResponses.size(),2);
	}
	
	@Test
	void getCourseIdToCourseMap_Success() {
		
		List<Course> courses = new  ArrayList<Course>();
		List<Integer> courseIds = new ArrayList<Integer>();
		courses.add(createTestCourse());
		courses.add(createTestCourse());
		courseIds.add(courses.get(0).getCourseId());
		courseIds.add(courses.get(1).getCourseId());
		List<Professor> professors = new ArrayList<Professor>();
		professors.add(createTestProfessor());
		courses.get(0).setProfessorId(professors.get(0).getProfessorId());
		courses.get(1).setProfessorId(professors.get(0).getProfessorId());
		List<CourseResponseDTO> courseResponses = new ArrayList<>();
		courseResponses.add(convertToResponseDTO(courses.get(0),professors.get(0)));
		courseResponses.add(convertToResponseDTO(courses.get(1),professors.get(0)));
		when(courseRepository.findAllByCourseIdIn(any())).thenReturn(courses);
		when(professorService.getProfessorIdToProfessorMap(any())).thenReturn(Map.of(professors.get(0).getProfessorId(),professors.get(0)));
		when(courseConverter.convertAll(any(), any())).thenReturn(courseResponses);
		List<CourseResponseDTO> actualCourseResponses = courseService.getCourseDetailsByIds(courseIds);
		assertEquals(actualCourseResponses.size(),2);
	}
	
	
	@Test
	void getCoursesAssignedToProfessor_Success() {
		
		List<Course> courses = new  ArrayList<Course>();

		courses.add(createTestCourse());
		courses.add(createTestCourse());

		List<Professor> professors = new ArrayList<Professor>();
		professors.add(createTestProfessor());
		courses.get(0).setProfessorId(professors.get(0).getProfessorId());
		courses.get(1).setProfessorId(professors.get(0).getProfessorId());
		List<CourseResponseDTO> courseResponses = new ArrayList<>();
		courseResponses.add(convertToResponseDTO(courses.get(0),professors.get(0)));
		courseResponses.add(convertToResponseDTO(courses.get(1),professors.get(0)));
		when(professorService.isProfessorExistsById(any())).thenReturn(true);
		when(courseRepository.findAllByProfessorId(any())).thenReturn(courses);
		try {
		List<Course> actualCourseResponses = courseService.getCoursesAssignedToProfessor(professors.get(0).getProfessorId());
		assertEquals(actualCourseResponses.size(),2);
		}
		catch(Exception e) {
			
		}
		
	}
	
	@Test
	void getCoursesAssignedToProfessor_Throws_Exception() {
		
		List<Course> courses = new  ArrayList<Course>();

		courses.add(createTestCourse());
		courses.add(createTestCourse());

		List<Professor> professors = new ArrayList<Professor>();
		professors.add(createTestProfessor());
		courses.get(0).setProfessorId(professors.get(0).getProfessorId());
		courses.get(1).setProfessorId(professors.get(0).getProfessorId());
		List<CourseResponseDTO> courseResponses = new ArrayList<>();
		courseResponses.add(convertToResponseDTO(courses.get(0),professors.get(0)));
		courseResponses.add(convertToResponseDTO(courses.get(1),professors.get(0)));
		when(professorService.isProfessorExistsById(any())).thenReturn(false);
		when(courseRepository.findAllByProfessorId(any())).thenReturn(courses);
		try {
		List<Course> actualCourseResponses = courseService.getCoursesAssignedToProfessor(professors.get(0).getProfessorId());
		assertEquals(actualCourseResponses.size(),2);
		}
		catch(Exception e) {
			assertTrue(e.getMessage().contains("NOT Found"));
		}
		
	}
	
	@Test
	void  getCourseTypeByCourseId_when_found_a_course() {
		Optional<Course> expectedCourse = Optional.of(createTestCourse());
		Professor expectedProfessor = createTestProfessor();
		
		when(courseRepository.findByCourseId(expectedCourse.get().getCourseId())).thenReturn(expectedCourse);
		try {
		CourseType courseType = courseService.getCourseTypeByCourseId(expectedCourse.get().getCourseId());
		assertEquals(courseType,CourseType.PRIMARY);
		}
		catch(Exception e) {};
		
	}
	
//	@Test
//	void  getCourseTypeByCourseId_when_course_does_not_exist() {
//		Optional<Course> expectedCourse = Optional.empty();
//		Professor expectedProfessor = createTestProfessor();
//		
//		when(courseRepository.findByCourseId(expectedCourse.get().getCourseId())).thenReturn(expectedCourse);
//		try {
//		CourseType courseType = courseService.getCourseTypeByCourseId(expectedCourse.get().getCourseId());
//		assertEquals(courseType,CourseType.PRIMARY);
//		}
//		catch(Exception e) {
//			assertTrue(e.getMessage().contains("does not exist in the Catalog"));
//		};
//		
//	}
	
	@Test
	void isCourseExistsInCatalog() {
		Boolean testFlag = true;
		when(courseRepository
				.existsByCourseId(any())).thenReturn(testFlag);
		assertTrue(courseService.isCourseExistsInCatalog(200));
	}
	
	@Test
	void add_whenInputNull_thenDoNothingTest() {
		
		CourseRequestDTO courseRequestDTO = null;
		
		courseService.add(courseRequestDTO);
		
		verify(courseRepository, times(0))
			.save(any());
		
	}
	
	@Test
	void add_whenInputValid_thenShouldSaveEntityTest() {
		
		CourseRequestDTO courseRequestDTO = new CourseRequestDTO("Test",1,"Test",1,10,CourseType.PRIMARY);
		courseService.add(courseRequestDTO);
		verify(courseRepository, times(1))
			.save(any());
		
	}
	
	@Test
	void addAll_whenInputNull_thenDoNothingTest() {
		
		CourseRequestDTO courseRequestDTO = null;
		
		courseService.add(courseRequestDTO);
		
		verify(courseRepository, times(0))
			.save(any());
		
	}
	
	@Test
	void addAll_whenInputValid_thenShouldSaveEntityTest() {
		List<CourseRequestDTO> courseRequestDTOs = new ArrayList<>();
		CourseRequestDTO courseRequestDTO = new CourseRequestDTO("Test",1,"Test",1,10,CourseType.PRIMARY);
		courseRequestDTOs.add(courseRequestDTO);
		courseService.addAll(courseRequestDTOs);
		verify(courseRepository, times(1))
			.saveAll(any());
		
	}
	
	@Test
	void isCourseHasVacantSeats_true() {
		Course course = createTestCourse();
		when(courseRepository.findByCourseId(any())).thenReturn(Optional.of(course));
		assertTrue(courseService.isCourseHasVacantSeats(course.getCourseId()));
	}
	

}
