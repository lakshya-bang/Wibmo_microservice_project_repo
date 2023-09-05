package com.wibmo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.ReportCard;

@Repository
public interface ReportCardRepository extends CrudRepository<ReportCard, Integer>{

	/**
	 * Fetches report card by studentId and CourseId
	 * @param studentId
	 * @param courseId
	 * @return Optional<ReportCard>
	 */
	Optional<ReportCard> findByStudentIdAndCourseId(Integer studentId, Integer courseId);

	/**
	 * Fetches list of Report Cards for a student.
	 * @param studentId
	 * @return List<ReportCard>
	 */
	List<ReportCard> findAllByStudentId(Integer studentId);

	/**
	 * Fetches list of report cards by studentId and Semester.
	 * @param studentId
	 * @param semester
	 * @return List<ReportCard>
	 */
	List<ReportCard> findAllByStudentIdAndSemester(Integer studentId, Integer semester);

}
