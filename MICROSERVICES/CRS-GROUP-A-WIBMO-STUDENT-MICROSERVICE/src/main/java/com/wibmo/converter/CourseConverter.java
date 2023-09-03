package com.wibmo.converter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wibmo.dto.CourseResponseDTO;
import com.wibmo.dto.CourseRequestDTO;
import com.wibmo.entity.Course;
import com.wibmo.entity.Professor;

/**
 * 
 */
@Service
public class CourseConverter {

	/**
	 * 
	 * @param course
	 * @param professor
	 * @return
	 */
	public CourseResponseDTO convert(Course course, Professor professor) {
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
	
	/**
	 * 
	 * @param courses
	 * @param professorIdToProfessorMap
	 * @return
	 */
	public List<CourseResponseDTO> convertAll(
			List<Course> courses, 
			Map<Integer, Professor> professorIdToProfessorMap) {
		return courses
				.stream()
				.map(course -> convert(
						course, 
						professorIdToProfessorMap.get(course.getProfessorId())))
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param courseRequestDTO
	 * @return
	 */
	public Course convert(CourseRequestDTO courseRequestDTO) {
		Course course = new Course();
		course.setCourseTitle(courseRequestDTO.getCourseTitle());
		course.setSemester(courseRequestDTO.getSemester());
		course.setDepartment(courseRequestDTO.getDepartment());
		course.setProfessorId(courseRequestDTO.getProfessorId());
		course.setNoOfSeats(courseRequestDTO.getNoOfSeats());
		course.setCourseType(courseRequestDTO.getCourseType());
		course.setIsCancelled(Boolean.FALSE);
		course.setYear(LocalDate.now().getYear());
		return course;
	}

	public List<Course> convertAll(Collection<CourseRequestDTO> courseRequestDTOs) {
		return courseRequestDTOs
				.stream()
				.map(courseRequestDTO -> convert(courseRequestDTO))
				.collect(Collectors.toList());
	}
	
}
