/**
 * 
 */
package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dto.CardDTO;
import com.wibmo.dto.NetBankingDTO;
import com.wibmo.dto.UPIDTO;
import com.wibmo.enums.PaymentMode;
import com.wibmo.exceptions.CardDetailsNotFoundException;
import com.wibmo.exceptions.NetBankingDetailsNotFoundException;
import com.wibmo.exceptions.UPIDetailsNotFoundException;
import com.wibmo.service.PaymentServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.GET,
			value="/get/{courseRegistrationId}")
	public ResponseEntity getBill(
			@PathVariable Integer courseRegistrationId) {
		return new ResponseEntity(
				paymentService
					.getPaymentByCourseRegistrationId(courseRegistrationId), 
				HttpStatus.OK);
	}
	
	/*
	 * This API should only be authorized for Admins
	 */
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.PUT,
			value="/approve/cash/{courseRegistrationId}")
	public ResponseEntity approveCashPayment(
			@PathVariable Integer courseRegistrationId) {
		paymentService.approveCashPayment(courseRegistrationId);
		return new ResponseEntity(PaymentMode.CASH.toString() + " Payment Approved!", HttpStatus.OK);
	}
	
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.PUT,
			value="/pay/card/{courseRegistrationId}")
	public ResponseEntity payByCard(
			@RequestBody CardDTO cardDTO,
			@PathVariable Integer courseRegistrationId) {
		try {
			paymentService.payByCard(cardDTO, courseRegistrationId);
			return new ResponseEntity("Thank you for paying through " + PaymentMode.CARD,HttpStatus.OK);
		}
		catch(CardDetailsNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.PUT,
			value="/pay/net-banking/{courseRegistrationId}")
	public ResponseEntity payByNetBanking(
			@RequestBody NetBankingDTO netBankingDTO,
			@PathVariable Integer courseRegistrationId) {
		try {
			paymentService.payByNetBanking(netBankingDTO, courseRegistrationId);
			return new ResponseEntity("Thank you for paying through " + PaymentMode.NETBANKING,HttpStatus.OK);
		} catch(NetBankingDetailsNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.PUT,
			value="/pay/upi/{courseRegistrationId}")
	public ResponseEntity payByUPI(
			@RequestBody UPIDTO upiDTO,
			@PathVariable Integer courseRegistrationId) {
		try {
			paymentService.payByUPI(upiDTO, courseRegistrationId);
			return new ResponseEntity("Thank you for paying through " + PaymentMode.UPI,HttpStatus.OK);
		}
		catch(UPIDetailsNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
}
