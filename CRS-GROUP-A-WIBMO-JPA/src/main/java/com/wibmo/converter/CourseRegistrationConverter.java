/**
 * 
 */
package com.wibmo.converter;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
@Service
public class CourseRegistrationConverter {

	public CourseRegistration convert(
			CourseRegistrationRequestDTO courseRegistrationRequestDTO) {
		CourseRegistration courseRegistration = new CourseRegistration();
		courseRegistration.setStudentId(
				courseRegistrationRequestDTO.getStudentId());
		courseRegistration.setSemester(
				courseRegistrationRequestDTO.getSemester());
		courseRegistration.setYear(LocalDate.now().getYear());
		courseRegistration.setPrimaryCourse1Id(
				courseRegistrationRequestDTO.getPrimaryCourseIds().get(0));
		courseRegistration.setPrimaryCourse2Id(
				courseRegistrationRequestDTO.getPrimaryCourseIds().get(1));
		courseRegistration.setPrimaryCourse3Id(
				courseRegistrationRequestDTO.getPrimaryCourseIds().get(2));
		courseRegistration.setPrimaryCourse4Id(
				courseRegistrationRequestDTO.getPrimaryCourseIds().get(3));
		courseRegistration.setAlternativeCourse1Id(
				courseRegistrationRequestDTO.getAlternativeCourseIds().get(0));
		courseRegistration.setAlternativeCourse2Id(
				courseRegistrationRequestDTO.getAlternativeCourseIds().get(1));
		courseRegistration.setRegistrationStatus(RegistrationStatus.PENDING);
		return courseRegistration;
	}
}
