package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
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
	
	@Query(value="SELECT user_v2.student.*,sub.course_id FROM user_v2.student INNER JOIN (SELECT user_v2.course.course_id,user_v2.registered_courses.student_id FROM "
			+ "user_v2.course INNER JOIN user_v2.registered_courses on "+
			"user_v2.registered_courses.primary_course_1_id=user_v2.course.course_id or "+
			"user_v2.registered_courses.primary_course_2_id=user_v2.course.course_id or "+
			"user_v2.registered_courses.primary_course_3_id=user_v2.course.course_id or "+
			"user_v2.registered_courses.primary_course_4_id=user_v2.course.course_id or "+
			"user_v2.registered_courses.alternative_course_1_id=user_v2.course.course_id or "+
			"user_v2.registered_courses.alternative_course_2_id=user_v2.course.course_id "+
			"where user_v2.course.professor_id=?1) sub on sub.student_id = user_v2.student.student_id;",nativeQuery=true)
	List<Tuple> getCourseIdToRegisteredStudentsMappingByProfessorId(Integer professorId);
}
