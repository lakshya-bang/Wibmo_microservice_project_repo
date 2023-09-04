/**
 * 
 */
package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.dto.CardDTO;
import com.wibmo.entity.Payment;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.service.PaymentServiceImpl;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class PaymentDAOTest {

	@InjectMocks
	PaymentServiceImpl paymentService;
	
	@Mock
	PaymentRepository paymentRepository;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void addTest() {
		Payment payment = new Payment();
		payment.setCourseRegistrationId(207);
		payment.setPaymentId(3);
		payment.setPaymentMode(PaymentMode.CASH);
		payment.setTotalAmount(4500);
		payment.setPendingAmount(4500);
		
		paymentService.add(payment);
		verify(paymentRepository, times(1))
		.save(payment);
	}
	
	@Test
	void getPaymentByCourseRegistrationIdTest() {
		Optional<Payment> expectedPayment = Optional.of(new Payment());
		expectedPayment.get().setCourseRegistrationId(207);
		expectedPayment.get().setPaymentId(3);
		expectedPayment.get().setPaymentMode(PaymentMode.CASH);
		expectedPayment.get().setTotalAmount(4500);
		expectedPayment.get().setPendingAmount(4500);
		
		when(paymentRepository.findByCourseRegistrationId(207)).thenReturn(expectedPayment);
		
		Payment actualPayment = paymentService.getPaymentByCourseRegistrationId(207);
		
		assertNotNull(actualPayment);
		assertEquals(expectedPayment.get(), actualPayment);
	}
	
//	@Test
//	void payByCardTest() {
//		CardDTO cardDTO = new CardDTO();
//		cardDTO.setCardNumber(12341234L);
//		cardDTO.setCvv(654);
//		cardDTO.setExpiryMonth(30);
//		cardDTO.setExpiryYear(27);
//		
//		Boolean expectedPayment = true;
//		when(paymentService.verfiy(cardDTO)).thenReturn(expectedPayment);
//		
//		Payment payment = new Payment();
//		payment.setCourseRegistrationId(207);
//		payment.setPaymentId(3);
//		payment.setPaymentMode(PaymentMode.CASH);
//		payment.setTotalAmount(4500);
//		payment.setPendingAmount(4500);
//		
//		when(paymentService.pay).thenReturn(payment);
//		
//		Boolean actualPayment = paymentService.payByCard(cardDTO, 207);
//		assertEquals(expectedPayment, actualPayment);
//	}

}
