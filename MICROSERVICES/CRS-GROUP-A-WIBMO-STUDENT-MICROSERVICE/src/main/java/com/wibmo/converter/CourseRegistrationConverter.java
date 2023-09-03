/**
 * 
 */
package com.wibmo.converter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dto.CourseRegistrationRequestDTO;
import com.wibmo.dto.CourseRegistrationResponseDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.CourseRegistration;
import com.wibmo.entity.Professor;
import com.wibmo.enums.RegistrationStatus;

/**
 * 
 */
@Service
public class CourseRegistrationConverter {
	
	@Autowired
	private CourseConverter courseConverter;
	
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
	
	public CourseRegistrationResponseDTO convert(
			CourseRegistration courseRegistration,
			Map<Integer, Course> courseIdToCourseMap,
			Map<Integer, Professor> professorIdToProfessorMap) {
		return new CourseRegistrationResponseDTO(
				courseRegistration.getRegistrationId(),
				courseRegistration.getStudentId(),
				courseRegistration.getSemester(),
				courseConverter.convertAll(
						List.of(courseIdToCourseMap.get(courseRegistration.getPrimaryCourse1Id()),
								courseIdToCourseMap.get(courseRegistration.getPrimaryCourse2Id()),
								courseIdToCourseMap.get(courseRegistration.getPrimaryCourse3Id()),
								courseIdToCourseMap.get(courseRegistration.getPrimaryCourse4Id())),
						professorIdToProfessorMap),
				courseConverter.convertAll(
						List.of(courseIdToCourseMap.get(courseRegistration.getAlternativeCourse1Id()),
								courseIdToCourseMap.get(courseRegistration.getAlternativeCourse2Id())),
						professorIdToProfessorMap),
				courseRegistration.getRegistrationStatus());
	}

	public List<CourseRegistrationResponseDTO> convertAll(
			List<CourseRegistration> courseRegistrations,
			Map<Integer, Course> courseIdToCourseMap,
			Map<Integer, Professor> professorIdToProfessorMap) {
		return courseRegistrations
				.stream()
				.map(courseRegistration -> convert(
						courseRegistration,
						courseIdToCourseMap,
						professorIdToProfessorMap))
				.collect(Collectors.toList());
	}
}
