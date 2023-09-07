/**
 * 
 */
package com.wibmo.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wibmo.dto.CardDTO;
import com.wibmo.dto.NetBankingDTO;
import com.wibmo.dto.UPIDTO;
import com.wibmo.entity.Notification;
import com.wibmo.enums.PaymentMode;
import com.wibmo.exceptions.CardDetailsNotFoundException;
import com.wibmo.exceptions.NetBankingDetailsNotFoundException;
import com.wibmo.exceptions.UPIDetailsNotFoundException;
import com.wibmo.service.NotificationService;
import com.wibmo.service.NotificationServiceImpl;
import com.wibmo.service.PaymentServiceImpl;

/**
 * 
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private PaymentServiceImpl paymentService;
	
	@Autowired
	private NotificationServiceImpl notificationService;
	/**
	 * Api to get Bill for payment made by course registration id
	 * @param courseRegistrationId id from registered courses to check bill
	 * @return Bill or student with courseRegistrationId given
	 */
	@PreAuthorize("hasAuthority('Role.STUDENT')")
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

	/**
	 * This API should only be authorized for Admins to approve cash payments
	 * @param courseRegistrationId id from registered courses to approve cash payments
	 * @return Http status for if payment is approved or not
	 */
	@PreAuthorize("hasAuthority('Role.ADMIN')")
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.PUT,
			value="/approve/cash/{courseRegistrationId}")
	public ResponseEntity approveCashPayment(
			@PathVariable Integer courseRegistrationId) {
		paymentService.approveCashPayment(courseRegistrationId);
		return new ResponseEntity(PaymentMode.CASH.toString() + " Payment Approved!", HttpStatus.OK);
	}
	
	/**
	 * This Api is used to make payment using card
	 * @param cardDTO Card Details to be entered from client
	 * @param courseRegistrationId id from registered courses to pay the bill of student
	 * @return Response message and Http status for if payment is approved or not
	 */
	@PreAuthorize("hasAuthority('Role.STUDENT')")
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.POST,
			value="/pay/card/{courseRegistrationId}")
	public ResponseEntity payByCard(
			@RequestBody CardDTO cardDTO,
			@PathVariable Integer courseRegistrationId,
			@RequestHeader(value="Authorization") String jwt) {
			Boolean paymentStatus=false;
		try {
			paymentStatus=paymentService.payByCard(cardDTO, courseRegistrationId);
			return notificationService.SendPaymentNotification(jwt.substring(7),paymentStatus);
		}
		catch(CardDetailsNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * This Api is used to make payment using NetBanking
	 * @param netBankingDTO net banking details to be entered by client
	 * @param courseRegistrationId id from registered courses to pay the bill of student
	 * @return Response message and Http status for if payment is approved or not
	 */
	@PreAuthorize("hasAuthority('Role.STUDENT')")
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.POST,
			value="/pay/net-banking/{courseRegistrationId}")
	public ResponseEntity payByNetBanking(
			@RequestBody NetBankingDTO netBankingDTO,
			@PathVariable Integer courseRegistrationId,
			@RequestHeader(value="Authorization") String jwt) {
		Boolean paymentStatus=false;
		try {
			paymentStatus= paymentService.payByNetBanking(netBankingDTO, courseRegistrationId);
			return notificationService.SendPaymentNotification(jwt.substring(7),paymentStatus);
		} catch(NetBankingDetailsNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * This Api is used to make payment using Upi
	 * @param upiDTO UPI details to be entered by client
	 * @param courseRegistrationId id from registered courses to pay the bill of student
	 * @return Response message and Http status for if payment is approved or not
	 */
	@PreAuthorize("hasAuthority('Role.STUDENT')")
	@RequestMapping(
			produces= MediaType.APPLICATION_JSON,
			method=RequestMethod.POST,
			value="/pay/upi/{courseRegistrationId}")
	public ResponseEntity payByUPI(
			@RequestBody UPIDTO upiDTO,
			@PathVariable Integer courseRegistrationId,
			@RequestHeader(value="Authorization") String jwt) {
		Boolean paymentStatus=false;
		try {
			paymentStatus=paymentService.payByUPI(upiDTO, courseRegistrationId);
			
			return notificationService.SendPaymentNotification(jwt.substring(7),paymentStatus);
		}
		catch(UPIDetailsNotFoundException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
}
