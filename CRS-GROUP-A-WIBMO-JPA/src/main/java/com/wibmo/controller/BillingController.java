/**
 * 
 */
package com.wibmo.controller;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wibmo.dto.PaymentDebitDTO;
import com.wibmo.dto.PaymentNetBankingDTO;
import com.wibmo.dto.PaymentUPIDTO;
import com.wibmo.entity.Bill;
import com.wibmo.entity.User;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.service.BillingServiceImpl;

/**
 * 
 */
//@RestController
//public class BillingController {
//	
//	@Autowired
//	private BillingServiceImpl billService;
//	
//	@RequestMapping(produces= MediaType.APPLICATION_JSON,
//			method=RequestMethod.GET,
//			value="/getBill/{studentId}"
//			)
//	public ResponseEntity getBill(@PathVariable Integer studentId) {
//		try {
//			Bill bill = billService.viewBillByStudentId(studentId);
//			return new ResponseEntity(bill,HttpStatus.OK);
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error !!!!",HttpStatus.NOT_FOUND);
//		}
//		
//	}
//	@RequestMapping(produces= MediaType.APPLICATION_JSON,
//			method=RequestMethod.POST,
//			value="/payBill/Debit/{studentId}"
//			)
//	public ResponseEntity payBillByDebit(@RequestBody PaymentDebitDTO paymentDebitDTO ,@PathVariable Integer studentId) {
//		try {
//			billService.payBillByStudentIdWithDebit(paymentDebitDTO,studentId);
//
//			return new ResponseEntity("Thank you for paying through" + PaymentMode.DEBIT,HttpStatus.OK);
//
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Transaction Failed "+e.getMessage(),HttpStatus.NOT_FOUND);
//		}
//		
//	}
//	@RequestMapping(produces= MediaType.APPLICATION_JSON,
//			method=RequestMethod.POST,
//			value="/payBill/NetBanking/{studentId}"
//			)
//	public ResponseEntity payBillByNetBanking(@RequestBody PaymentNetBankingDTO paymentNetBankingDTO ,@PathVariable Integer studentId) {
//		try {
//			billService.payBillByStudentIdWithNetBanking(paymentNetBankingDTO,studentId);
//
//			return new ResponseEntity("Thank you for paying through" + PaymentMode.NETBANKING,HttpStatus.OK);
//
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error !!!!",HttpStatus.NOT_FOUND);
//		}
//		
//	}
//	@RequestMapping(produces= MediaType.APPLICATION_JSON,
//			method=RequestMethod.POST,
//			value="/payBill/UPI/{studentId}"
//			)
//	public ResponseEntity payBillByDebit(@RequestBody PaymentUPIDTO paymentUPIDTO ,@PathVariable Integer studentId) {
//		try {
//			billService.payBillByStudentIdWithUPI(paymentUPIDTO,studentId);
//
//			return new ResponseEntity("Thank you for paying through" + PaymentMode.UPI,HttpStatus.OK);
//
//		}
//		catch(Exception e) {
//			return new ResponseEntity("Internal Server Error !!!!",HttpStatus.NOT_FOUND);
//		}
//		
//	}
//}
