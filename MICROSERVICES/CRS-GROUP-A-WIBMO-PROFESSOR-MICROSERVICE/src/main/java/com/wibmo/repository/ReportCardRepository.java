package com.wibmo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.ReportCard;

@Repository
public interface ReportCardRepository extends CrudRepository<ReportCard, Integer>{

	Optional<ReportCard> findByStudentIdAndCourseId(Integer studentId, Integer courseId);

	List<ReportCard> findAllByStudentId(Integer studentId);

	List<ReportCard> findAllByStudentIdAndSemester(Integer studentId, Integer semester);

}
