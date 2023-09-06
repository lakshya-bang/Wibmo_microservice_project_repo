package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.*;

 

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.wibmo.converter.CourseConverter;
import com.wibmo.converter.CourseRegistrationConverter;
import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.dto.CourseRequestDTO;
import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.entity.Admin;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Payment;
import com.wibmo.entity.Professor;
import com.wibmo.entity.User;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.AdminService;
import com.wibmo.service.AdminServiceImpl;

 

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

 

@ExtendWith(MockitoExtension.class)
class TestAdminDAO {

     @InjectMocks
     AdminServiceImpl adminService;

     @Mock
     AdminRepository adminRepository;
     
     @Mock
     CourseRepository courseRepository;
     
     @Spy
     @Autowired
 	CourseConverter courseConverter;
     
     @Spy
     @Autowired
     CourseRegistrationConverter courseRegistrationConverter;
     
     @Mock
     ProfessorRepository professorRepository;
     
     @Mock
     PaymentRepository paymentRepository;
     
     @Mock
     CourseRegistrationRepository courseRegistrationRepository;
     
     @Mock
     UserRepository userRepository;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }
    
    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }
    
    @Test
    void findAllAdmin() {
         List<Admin> list = new ArrayList<Admin>();
            Admin adminOne = new Admin();
            Admin adminTwo = new Admin();
            adminOne.setAdminName("bob123@gmail.com");
            adminTwo.setAdminEmail("bob123@gmail.com");

            adminOne.setAdminName("RC");
            adminTwo.setAdminEmail("RC");

            list.add(adminOne);
            list.add(adminTwo);

            when(adminRepository.findAll()).thenReturn(list);
            List<Admin> empList = adminService.getAllAdmins();

            assertEquals(2, empList.size());

    }

    @Test
    void getAdminById_WhenAdminNotFound_ShouldReturnNullTest() {
    	Optional<Admin> dummyAdminOptional =Optional.empty();

    	
    	Integer adminId=1;
    	when(adminRepository.findByAdminId(adminId)).thenReturn(dummyAdminOptional);
    	Admin admin = adminService.getAdminById(adminId);
    	assertEquals(null, admin);
    }
    
    @Test
    void getAdminById_WhenAdminisFound_ShouldReturnAdminTest() {
    	Optional<Admin> dummyAdminOptional = Optional.of(
    			new Admin(
    						3000,					//adminId
    						new Random().nextInt(), //userId
    						"abc@gmail.com",		//adminEmail
    						"abc"					//adminName
    					)
    			);
    			when(adminRepository.findByAdminId(any(Integer.class)))
    			.thenReturn(dummyAdminOptional);
    			
    			Admin admin=adminService.getAdminById(3000);
    			
    			assertNotNull(admin);
    			assertEquals(dummyAdminOptional.get(), admin);
    			
    }
    @Test
	void add_whenInputNull_thenDoNothingTest() {
		
		CourseRequestDTO courseRequestDTO = null;
		
		adminService.add(courseRequestDTO);
		
		verify(courseRepository, times(0))
			.save(any());
		
	}
	
	@Test
	void add_whenInputValid_thenShouldSaveEntityTest() {
		
		CourseRequestDTO courseRequestDTO = new CourseRequestDTO("Test",1,"Test",1,10,CourseType.PRIMARY);
		adminService.add(courseRequestDTO);
		verify(courseRepository, times(1))
			.save(any());
		
	}
	
	@Test
	void addAll_whenInputNull_thenDoNothingTest() {
		
		CourseRequestDTO courseRequestDTO = null;
		
		adminService.add(courseRequestDTO);
		
		verify(courseRepository, times(0))
			.save(any());
		
	}
	
	@Test
	void addAll_whenInputValid_thenShouldSaveEntityTest() {
		List<CourseRequestDTO> courseRequestDTOs = new ArrayList<>();
		CourseRequestDTO courseRequestDTO = new CourseRequestDTO("Test",1,"Test",1,10,CourseType.PRIMARY);
		courseRequestDTOs.add(courseRequestDTO);
		adminService.addAll(courseRequestDTOs);
		verify(courseRepository, times(1))
			.saveAll(any());
		
	}
	
	@Test
	void updateAccountRegistrationStatusToByUserIds_whenInputNull_thenDoNullTest() throws UserNotFoundException {
		Collection<Integer> userIds =new ArrayList<Integer>();
		List<User> check  =new ArrayList<User>();
//		userIds.add(1);
//		userIds.add(2);
//		when(userRepository.findAllByUserIdIn(userIds)).thenReturn(check);
		Boolean actual=adminService.updateAccountRegistrationStatusToByUserIds(RegistrationStatus.PENDING, userIds);

		assertEquals(actual,false);
	}
	
	@Test
	void updateAccountRegistrationStatusToByUserIds_whenInputIsThere_thenDoUpdateTest() throws UserNotFoundException {
		Collection<Integer> userIds =new ArrayList<Integer>();
		List<User> check  =new ArrayList<User>();
		userIds.add(1);
		userIds.add(2);
		check.add(new User(1,"a@gmail.com",UserType.ADMIN,RegistrationStatus.PENDING));
		check.add(new User(2,"b@gmail.com",UserType.ADMIN,RegistrationStatus.PENDING));
		when(userRepository.findAllByUserIdIn(userIds)).thenReturn(check);
		Boolean actual=adminService.updateAccountRegistrationStatusToByUserIds(RegistrationStatus.PENDING, userIds);

		assertEquals(actual,true);
	}
	
	@Test
	void updateAllPendingAccountRegistrationsTo() {
		List<User> pendingAccounts = new ArrayList<User>();
		pendingAccounts.add(new User(1,"a@gmail.com",UserType.ADMIN,RegistrationStatus.PENDING));
		pendingAccounts.add(new User(2,"b@gmail.com",UserType.ADMIN,RegistrationStatus.PENDING));
		when(userRepository.findAllByRegistrationStatus(RegistrationStatus.PENDING)).thenReturn(pendingAccounts);
		assertTrue(adminService.updateAllPendingAccountRegistrationsTo(RegistrationStatus.PENDING));
	}
   @Test
    void testGetRegistrationStatusByStudentIdAndSemester() throws Exception {
        // Mocking data
        Integer studentId = 1;
        Integer semester = 1;
        CourseRegistration courseRegistration = new CourseRegistration();
        courseRegistration.setRegistrationStatus(RegistrationStatus.APPROVED);

        // Mocking repository methods
        when(courseRegistrationRepository.findByStudentIdAndSemester(studentId, semester))
                .thenReturn(Optional.of(courseRegistration));

        // Calling the method
        RegistrationStatus result = adminService.getRegistrationStatusByStudentIdAndSemester(studentId, semester);

        // Verifying the result
        assertEquals(RegistrationStatus.APPROVED, result);
    }
   @Test
   void testUpdateCourseRegistrationStatusToByRegistrationIds() throws Exception {
       // Mocking data
       RegistrationStatus registrationStatus = RegistrationStatus.APPROVED;
       Collection<Integer> courseRegistrationIds = Collections.singletonList(1);
       CourseRegistration courseRegistration = new CourseRegistration();
       courseRegistration.setRegistrationId(1);
       courseRegistration.setRegistrationStatus(RegistrationStatus.PENDING);
       List<CourseRegistration> courseRegistrations = Collections.singletonList(courseRegistration);
       Payment payment = new Payment();
       payment.setPendingAmount(0);

       // Mocking repository methods
       when(courseRegistrationRepository.findAllByRegistrationIdIn(courseRegistrationIds))
               .thenReturn(courseRegistrations);
       when(paymentRepository.findByCourseRegistrationId(courseRegistration.getRegistrationId()))
               .thenReturn(Optional.of(payment));

       // Calling the method
       Boolean result = adminService.updateCourseRegistrationStatusToByRegistrationIds(registrationStatus, courseRegistrationIds);

       // Verifying the result
       assertTrue(result);
       assertEquals(RegistrationStatus.APPROVED, courseRegistration.getRegistrationStatus());
   }


    @Test
    void testAddAll() {
        // Mocking data
        Collection<CourseRequestDTO> courseRequestDTOs = Collections.singletonList(new CourseRequestDTO());

        // Calling the method
        Boolean result = adminService.addAll(courseRequestDTOs);

        // Verifying the result
        assertTrue(result);
        verify(courseRepository, times(1)).saveAll(anyCollection());
    }

    @Test
    void testRemoveCourseById() throws Exception {
        // Mocking data
        Integer courseId = 1;
        Course course = new Course();
        course.setCourseId(courseId);
        course.setProfessorId(null);

        // Mocking repository methods
        when(courseRepository.findByCourseId(courseId)).thenReturn(Optional.of(course));

        // Calling the method
        Boolean result = adminService.removeCourseById(courseId);

        // Verifying the result
        assertTrue(result);
        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    void testAssignCourseToProfessor() throws Exception {
        // Mocking data
        Integer courseId = 1;
        Integer professorId = 1;
        Course course = new Course();
        course.setCourseId(courseId);
        // Mocking repository methods
        when(courseRepository.findByCourseId(courseId)).thenReturn(Optional.of(course));
        when(professorRepository.existsByProfessorId(professorId)).thenReturn(true);

        // Calling the method
        adminService.assignCourseToProfessor(courseId, professorId);

        // Verifying the result
        assertEquals(professorId, course.getProfessorId());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testGetRegisteredCourseIdsByRegistrationId() {
        // Mocking data
        Integer registrationId = 1;
        CourseRegistration courseRegistration = new CourseRegistration();
        courseRegistration.setPrimaryCourse1Id(1);

        // Mocking repository methods
        when(courseRegistrationRepository.findByRegistrationId(registrationId))
                .thenReturn(Optional.of(courseRegistration));

        // Calling the method
        List<Integer> result = adminService.getRegisteredCourseIdsByRegistrationId(registrationId);

        // Verifying the result
        assertEquals(1, result.size());
        assertEquals(1, result.get(0));
    }
    @Test
    void testAdd() {
        // Mocking data
        CourseRequestDTO courseRequestDTO = new CourseRequestDTO();

        // Calling the method
        Boolean result = adminService.add(courseRequestDTO);

        // Verifying the result
        assertTrue(result);
        verify(courseRepository, times(1)).save(any());
    }
    
	@Test
  void testGetRegisteredCoursesByStudentIdAndSemester() throws Exception {
		
  }



  @Test
  void testGetCourseRegistrationsByRegistrationStatus_ifOnlyPending_returnAllPending() {
	// Mock data
	  List<CourseRegistration> courseRegistration = new  ArrayList<CourseRegistration>();
		courseRegistration.add(new CourseRegistration(1, 1,2011,1, 1, 2, 3, 4, 5, 6, RegistrationStatus.PENDING));
		courseRegistration.add(new CourseRegistration(2, 1,2011,1, 1, 2, 3, 4, 5, 6, RegistrationStatus.PENDING));
		List<Integer> courseIds = getCourseIds(courseRegistration);
		List<Integer> professorId = new ArrayList<Integer>();
		professorId.add(1);
		List<Course> courses = new ArrayList<Course>();
		courses.add(new Course(1, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(2, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(3, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(4, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(5, "a", 1, 2011, "CSE", 1, false, 10, CourseType.ALTERNATIVE));
		courses.add(new Course(6, "a", 1, 2011, "CSE", 1, false, 10, CourseType.ALTERNATIVE));
		List<Integer> professorIds = new ArrayList<Integer>();
		List<Professor> professors = new ArrayList<Professor>();
		professorIds.add(1);
		List<CourseRegistrationResponseDTO> courseRegistrationResponseDTOs= new ArrayList<CourseRegistrationResponseDTO>();
//		courseRegistrationResponseDTOs.add(new CourseRegistrationResponseDTO(1, 1, 1, new ArrayList<CourseResponseDTO>(), null, null));
		professors.add(new Professor(1, 1, "a@gmail.com", "Hota", "CSE"));
		
		when(courseRepository.findAllByCourseIdIn(any(List.class))).thenReturn(courses);
		when(professorRepository.findAllByProfessorIdIn(any(List.class))).thenReturn(professors);
		when(courseRegistrationRepository.findAllByRegistrationStatus(any(RegistrationStatus.class)))
		.thenReturn(courseRegistration);
		
		Map<Integer, Course> courseIdToCourseMap = adminService.getcourseIdToCourseMap(courseIds);
		Map<Integer,Professor> professorIdToProfessorMap = adminService.getprofessorIdToProfessorMap(professorIds);
		System.out.println(courseRegistrationConverter + " " + courseConverter);
		Object[] objects = new Object[1];
		objects[0] = courseConverter;
		courseRegistrationResponseDTOs = courseRegistrationConverter.
				convertAll(courseRegistration, courseIdToCourseMap, professorIdToProfessorMap,objects);
		
		
		List<CourseRegistrationResponseDTO> actualCourseRegistrationResponse=adminService.
				getCourseRegistrationsByRegistrationStatus(RegistrationStatus.PENDING);
		assertEquals(courseRegistrationResponseDTOs.size(),actualCourseRegistrationResponse.size());
		
  }

  @Test
  void testGetCourseRegistrationsByRegistrationStatus_whenmultipleStatus_shouldReturnOnlyPending() {
	// Mock data
	  List<CourseRegistration> courseRegistration = new  ArrayList<CourseRegistration>();
		courseRegistration.add(new CourseRegistration(1, 1,2011,1, 1, 2, 3, 4, 5, 6, RegistrationStatus.PENDING));
		courseRegistration.add(new CourseRegistration(2, 1,2011,1, 1, 2, 3, 4, 5, 6, RegistrationStatus.PENDING));
		courseRegistration.add(new CourseRegistration(3, 1,2011,1, 1, 2, 3, 4, 5, 6, RegistrationStatus.APPROVED));
		courseRegistration.add(new CourseRegistration(4, 1,2011,1, 1, 2, 3, 4, 5, 6, RegistrationStatus.REJECTED));
		List<Integer> courseIds = getCourseIds(courseRegistration);
		List<Integer> professorId = new ArrayList<Integer>();
		professorId.add(1);
		List<Course> courses = new ArrayList<Course>();
		courses.add(new Course(1, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(2, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(3, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(4, "a", 1, 2011, "CSE", 1, false, 10, CourseType.PRIMARY));
		courses.add(new Course(5, "a", 1, 2011, "CSE", 1, false, 10, CourseType.ALTERNATIVE));
		courses.add(new Course(6, "a", 1, 2011, "CSE", 1, false, 10, CourseType.ALTERNATIVE));
		List<Integer> professorIds = new ArrayList<Integer>();
		List<Professor> professors = new ArrayList<Professor>();
		professorIds.add(1);
		List<CourseRegistrationResponseDTO> courseRegistrationResponseDTOs= new ArrayList<CourseRegistrationResponseDTO>();
//		courseRegistrationResponseDTOs.add(new CourseRegistrationResponseDTO(1, 1, 1, new ArrayList<CourseResponseDTO>(), null, null));
		professors.add(new Professor(1, 1, "a@gmail.com", "Hota", "CSE"));
		
		when(courseRepository.findAllByCourseIdIn(any(List.class))).thenReturn(courses);
		when(professorRepository.findAllByProfessorIdIn(any(List.class))).thenReturn(professors);
		when(courseRegistrationRepository.findAllByRegistrationStatus(any(RegistrationStatus.class)))
		.thenReturn(courseRegistration.subList(0, 2));
		
		Map<Integer, Course> courseIdToCourseMap = adminService.getcourseIdToCourseMap(courseIds);
		Map<Integer,Professor> professorIdToProfessorMap = adminService.getprofessorIdToProfessorMap(professorIds);
		System.out.println(courseRegistrationConverter + " " + courseConverter);
		Object[] objects = new Object[1];
		objects[0] = courseConverter;
		courseRegistrationResponseDTOs = courseRegistrationConverter.
				convertAll(courseRegistration, courseIdToCourseMap, professorIdToProfessorMap,objects);
		
		
		List<CourseRegistrationResponseDTO> actualCourseRegistrationResponse=adminService.
				getCourseRegistrationsByRegistrationStatus(RegistrationStatus.PENDING);
		assertEquals(2,actualCourseRegistrationResponse.size());
		
  }

 

//  @Test
//  void testUpdateAllPendingCourseRegistrationsTo() throws Exception {
//      // Mocking data
//      RegistrationStatus registrationStatus = RegistrationStatus.APPROVED;
//      List<CourseRegistration> courseRegistrations = Collections.singletonList(new CourseRegistration());
//      Payment payment = new Payment();
//      payment.setPendingAmount(0);
//
//      // Mocking repository methods
//      when(courseRegistrationRepository.findAllByRegistrationStatus(RegistrationStatus.PENDING))
//              .thenReturn(courseRegistrations);
//      when(paymentRepository.findByCourseRegistrationId(any(Integer.class))).thenReturn(Optional.of(payment));
//
//      // Calling the method
//      Boolean result = adminService.updateAllPendingCourseRegistrationsTo(registrationStatus);
//
//      // Verifying the result
//      assertTrue(result);
//      assertEquals(RegistrationStatus.APPROVED, courseRegistrations.get(0).getRegistrationStatus());
//  }
//  ---------------------------Utility Methods---------------------------
  /**
	 * Fetches the Course Ids based on given List of courseRegistration
	 * @param courseRegistrations
	 * @return
	 */
	private List<Integer> getCourseIds(List<CourseRegistration> courseRegistrations) {
		List<Integer> courseIds = new ArrayList<>();
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse1Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse2Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse3Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getPrimaryCourse4Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getAlternativeCourse1Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		courseIds.addAll(
				courseRegistrations
					.stream()
					.map(courseRegistration -> courseRegistration.getAlternativeCourse2Id())
					.filter(Objects::nonNull)
					.collect(Collectors.toSet()));
		return courseIds;
	}
	
}


