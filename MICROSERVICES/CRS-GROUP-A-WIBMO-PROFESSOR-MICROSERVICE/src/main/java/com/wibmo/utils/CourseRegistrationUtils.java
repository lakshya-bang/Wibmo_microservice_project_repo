/**
 * 
 */
package com.wibmo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wibmo.entity.CourseRegistration;
import com.wibmo.enums.CourseType;
import com.wibmo.enums.UserType;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.CourseRegistrationRepository;

/**
 * 
 */
@Component
public class CourseRegistrationUtils {
	
	@Autowired
	CourseRegistrationRepository courseRegistrationRepository;
	
	@Autowired
	CourseUtils courseUtils;
	
	@Autowired
	StudentUtils studentUtils;
	
	public CourseRegistration getCourseRegistrationByStudentIdAndSemester(Integer studentId, Integer semester) {
		return courseRegistrationRepository
				.findByStudentIdAndSemester(studentId, semester)
				.map(courseRegistration -> courseRegistration)
				.orElse(null);
		
	}
	
	public Boolean hasRegistrationByStudentIdAndCourseId(
			Integer studentId, Integer courseId) 
				throws 
					UserNotFoundException, 
					CourseNotExistsInCatalogException {
		
		if(studentUtils.isStudentExistsById(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		
		if(courseUtils.isCourseExistsInCatalog(courseId)) {
			throw new CourseNotExistsInCatalogException(courseId);
		}
		
		CourseType courseType = courseUtils.getCourseTypeByCourseId(courseId);
		
		if(CourseType.ALTERNATIVE.equals(courseType)) {
			return courseRegistrationRepository.existsByStudentIdAndAlternativeCourse1Id(studentId, courseId)
				|| courseRegistrationRepository.existsByStudentIdAndAlternativeCourse2Id(studentId, courseId);
		}
		
		return courseRegistrationRepository.existsByStudentIdAndPrimaryCourse1Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse2Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse3Id(studentId, courseId)
			|| courseRegistrationRepository.existsByStudentIdAndPrimaryCourse4Id(studentId, courseId);
	}
}
