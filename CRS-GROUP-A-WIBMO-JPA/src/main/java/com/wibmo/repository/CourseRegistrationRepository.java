package com.wibmo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;


public interface CourseRegistrationRepository {

	RegistrationStatus findRegistrationStatusByStudent(Student student);

	Integer findCourseRegistrationIdByStudent(Student student);

	Object findFirstVacantAlternativeCourseIdIndexByCourseRegistrationId(Integer courseRegistrationId);

	void setAlternativeCourseIdAsAtIndexByCourseRegistrationId(Integer courseId,
			Object findFirstVacantAlternativeCourseIdIndexByCourseRegistrationId, Integer courseRegistrationId);

	Object findFirstVacantPrimaryCourseIdIndexByCourseRegistrationId(Integer courseRegistrationId);

	void setPrimaryCourseIdAsAtIndexByCourseRegistrationId(Integer courseId,
			Object findFirstVacantPrimaryCourseIdIndexByCourseRegistrationId, Integer courseRegistrationId);

	Object findAlternativeCourseIdIndexByCourseRegistrationIdForCourse(Integer courseRegistrationId, Integer courseId);

	void setAlternativeCourseIdAsNullAtIndexByCourseRegistrationId(
			Object findAlternativeCourseIdIndexByCourseRegistrationIdForCourse, Integer courseRegistrationId);

	Object findPrimaryCourseIdIndexByCourseRegistrationIdForCourse(Integer courseRegistrationId, Integer courseId);

	void setPrimaryCourseIdAsNullAtIndexByCourseRegistrationId(
			Object findPrimaryCourseIdIndexByCourseRegistrationIdForCourse, Integer courseRegistrationId);

	Collection<Integer> findAllStudentIdsByCourseId(Integer courseId);

	CourseRegistration findByCourseIdAndSemesterAndYear(Integer courseId, Integer semester, Integer year);

	List<CourseRegistration> findAllByRegistrationStatus(RegistrationStatus registrationStatus);

	Boolean updateRegistrationStatusAsByIdIn(RegistrationStatus registrationStatus, Set<Integer> courseRegistrationIds);

	Boolean existsByStudent(Student student);

	Boolean existsByStudentAndCourseId(Student student, Integer courseId);

}
