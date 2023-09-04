package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.ReportCardServiceImpl;
import com.wibmo.service.StudentServiceImpl;

/**
 * @author abhishek.sharma
 */
@ExtendWith(MockitoExtension.class)
public class TestReportCardDAO {

	@InjectMocks
	private ReportCardServiceImpl reportCardService;
	
	@Mock
	private StudentServiceImpl studentService;
	
	@Mock
	private ReportCardRepository reportCardRepository;
	
	@Test
	void getSemesterToReportCardMapByStudentId_whenGradesNotAdded_thenThrowExceptionTest() {
		
		when(studentService.isStudentExistsById(any(Integer.class)))
			.thenReturn(Boolean.FALSE);
		
		assertThrows(
				UserNotFoundException.class,
				() -> reportCardService.getSemesterToReportCardMapByStudentId(new Random().nextInt()));
	}
	
	@Test
	void getSemesterToReportCardMapByStudentId_whenFound_whenStudentNotRegisteredForCourse_thenReturnEmptyMap() {
	
		// TODO
		
	}
}
