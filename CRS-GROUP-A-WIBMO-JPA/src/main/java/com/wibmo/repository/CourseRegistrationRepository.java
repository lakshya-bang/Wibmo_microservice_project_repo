package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.CourseRegistration;
import com.wibmo.enums.RegistrationStatus;

@Repository
public interface CourseRegistrationRepository extends CrudRepository<CourseRegistration, Integer>{

	Optional<CourseRegistration> findByRegistrationId(Integer registrationId);

	Optional<CourseRegistration> findByStudentIdAndSemester(Integer studentId, Integer semester);
	
	List<CourseRegistration> findAll();
	
	List<CourseRegistration> findAllByPrimaryCourse1IdIn(Collection<Integer> primaryCourse1Ids);
	
	List<CourseRegistration> findAllByPrimaryCourse2IdIn(Collection<Integer> primaryCourse2Ids);
	
	List<CourseRegistration> findAllByPrimaryCourse3IdIn(Collection<Integer> primaryCourse3Ids);
	
	List<CourseRegistration> findAllByPrimaryCourse4IdIn(Collection<Integer> primaryCourse4Ids);
	
	List<CourseRegistration> findAllByAlternativeCourse1IdIn(Collection<Integer> alternativeCourse1Ids);
	
	List<CourseRegistration> findAllByAlternativeCourse2IdIn(Collection<Integer> alternativeCourse2Ids);
	
	List<CourseRegistration> findAllByRegistrationIdIn(Collection<Integer> registrationIds);
	
	List<CourseRegistration> findAllByRegistrationStatus(RegistrationStatus registrationStatus);

	Boolean existsByStudentIdAndSemester(Integer studentId, Integer semester);

	Boolean existsByStudentIdAndPrimaryCourse1Id(Integer studentId, Integer primaryCourse1Id);
	
	Boolean existsByStudentIdAndPrimaryCourse2Id(Integer studentId, Integer primaryCourse2Id);
	
	Boolean existsByStudentIdAndPrimaryCourse3Id(Integer studentId, Integer primaryCourse3Id);
	
	Boolean existsByStudentIdAndPrimaryCourse4Id(Integer studentId, Integer primaryCourse4Id);
	
	Boolean existsByStudentIdAndAlternativeCourse1Id(Integer studentId, Integer alternativeCourse1Id);
	
	Boolean existsByStudentIdAndAlternativeCourse2Id(Integer studentId, Integer alternativeCourse2Id);

}
