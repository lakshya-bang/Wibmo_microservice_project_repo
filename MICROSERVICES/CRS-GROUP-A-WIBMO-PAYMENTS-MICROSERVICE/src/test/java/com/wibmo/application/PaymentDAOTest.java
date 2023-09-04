/**
 * 
 */
package com.wibmo.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wibmo.dto.CardDTO;
import com.wibmo.entity.Payment;
import com.wibmo.enums.PaymentMode;
import com.wibmo.exceptions.CardDetailsNotFoundException;
import com.wibmo.service.PaymentServiceImpl;

/**
 * 
 */
class PaymentDAOTest {

	
	@Autowired
	PaymentServiceImpl paymentService;
	

	@Test
	void payByCardTest() {
		CardDTO cardDTO = new CardDTO();
		cardDTO.setCardNumber(12341234L);
		cardDTO.setCvv(654);
		cardDTO.setExpiryMonth(06);
		cardDTO.setExpiryYear(30);
		
		when(paymentService.verfiy(cardDTO)).thenReturn(true);
	
		Payment payment = new Payment();
		payment.setCourseRegistrationId(207);
		payment.setPaymentId(3);;
		payment.setPaymentMode(PaymentMode.CARD);
		payment.setPendingAmount(4500);
		payment.setTotalAmount(4500);
		
		try {
			when(paymentService.payByCard(cardDTO, payment.getCourseRegistrationId())).thenReturn(true);
		} catch (CardDetailsNotFoundException e) {
			e.printStackTrace();
		}
	}

}
