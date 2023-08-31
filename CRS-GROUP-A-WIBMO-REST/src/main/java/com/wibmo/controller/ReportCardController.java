/**
 * 
 */
package com.wibmo.controller;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dto.ReportCardRequestDTO;
import com.wibmo.dto.ReportCardResponseDTO;
import com.wibmo.exception.StudentNotRegisteredForSemesterException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.ReportCardServiceImpl;

/**
 * @author abhishek.sharma
 */
@RestController
@RequestMapping("/report-card")
public class ReportCardController {

	@Autowired
	private ReportCardServiceImpl reportCardService;
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/get/{studentId}/{semester}")
	public ResponseEntity getReportCardForSemester(
			@PathVariable(value = "studentId") Integer studentId,
			@PathVariable(value = "semester") Integer semester){
		try {
			List<ReportCardResponseDTO> reportCards = reportCardService
					.getReportCardByStudentIdAndSemester(studentId, semester);
			return new ResponseEntity(reportCards, HttpStatus.OK);
		}
		catch(UserNotFoundException 
			| StudentNotRegisteredForSemesterException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.GET,
			value = "/get-all/{studentId}")
	public ResponseEntity getAllReportCards(
			@PathVariable(value = "studentId") Integer studentId){
		try {
			Map<Integer, List<ReportCardResponseDTO>> semesterToReportCardsMap = reportCardService
					.getSemesterToReportCardMapByStudentId(studentId);
			return new ResponseEntity(semesterToReportCardsMap, HttpStatus.OK);
		}
		catch(UserNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(
			produces = MediaType.APPLICATION_JSON,
			method = RequestMethod.POST,
			value = "/upload")
	public ResponseEntity upload(
			@RequestBody List<ReportCardRequestDTO> reportCardRequestDTOs){
		reportCardService.addAll(reportCardRequestDTOs);
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
