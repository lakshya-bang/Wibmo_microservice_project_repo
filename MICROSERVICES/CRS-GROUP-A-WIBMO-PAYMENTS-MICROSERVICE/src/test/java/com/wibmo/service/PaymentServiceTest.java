/**
 * 
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.dto.CardDTO;
import com.wibmo.dto.NetBankingDTO;
import com.wibmo.dto.UPIDTO;
import com.wibmo.entity.Card;
import com.wibmo.entity.NetBanking;
import com.wibmo.entity.Payment;
import com.wibmo.entity.UPI;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.exceptions.CardDetailsNotFoundException;
import com.wibmo.exceptions.NetBankingDetailsNotFoundException;
import com.wibmo.exceptions.UPIDetailsNotFoundException;
import com.wibmo.repository.CardRepository;
import com.wibmo.repository.NetBankingRepository;
import com.wibmo.repository.PaymentRepository;
import com.wibmo.repository.UPIRepository;
import com.wibmo.service.PaymentServiceImpl;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@InjectMocks
	PaymentServiceImpl paymentService;
	
	@Mock
	PaymentRepository paymentRepository;
	
	@Mock
	CardRepository cardRepository;
	
	@Mock
	NetBankingRepository netBankingRepository;
	
	@Mock
	UPIRepository upiRepository;
	
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
		Payment payment = new Payment(5001,
				201,
				4500,
				4500,
				PaymentMode.CASH);
		
		paymentService.add(payment);
		verify(paymentRepository, times(1))
		.save(payment);
	}
	
	@Test
	void getPaymentByCourseRegistrationIdTest() {
		Optional<Payment> expectedPayment = Optional.of(new Payment(5001,
				201,
				4500,
				4500,
				PaymentMode.CASH));
		
		when(paymentRepository.findByCourseRegistrationId(207)).thenReturn(expectedPayment);
		
		Payment actualPayment = paymentService.getPaymentByCourseRegistrationId(207);
		
		assertNotNull(actualPayment);
		assertEquals(expectedPayment.get(), actualPayment);
	}
	
	@Test
	void payByCardTest() {
		// Get your inputs ready
		// Already existing payment information
		Optional<Payment> pendingPaymentOptional = Optional.of(new Payment(
				5001,
				201,
				4500,
				4500,
				PaymentMode.CASH));
		
		CardDTO fakeCardDTO = new CardDTO();
		fakeCardDTO.setCardNumber(new Random().nextLong());
		fakeCardDTO.setExpiryMonth(new Random().nextInt());
		fakeCardDTO.setExpiryYear(new Random().nextInt());
		fakeCardDTO.setCvv(new Random().nextInt());
		
		Integer fakeCourseRegistrationId = new Random().nextInt();
		
		// Mock Database calls
		when(cardRepository
				.findByCardNumberAndExpiryMonthAndExpiryYearAndCvv(
						any(Long.class),
						any(Integer.class),
						any(Integer.class),
						any(Integer.class)))
			.thenReturn(Optional.of(new Card(
					new Random().nextLong(),
					new Random().nextInt(),
					new Random().nextInt(),
					new Random().nextInt(),
					"Dummy Card Holder")));
		
		when(paymentRepository
				.findByCourseRegistrationId(any(Integer.class)))
			.thenReturn(pendingPaymentOptional);
		
		try {
			paymentService.payByCard(fakeCardDTO, fakeCourseRegistrationId);	
		} catch (CardDetailsNotFoundException e) {
			e.printStackTrace();
		}
		verify(paymentRepository, times(1)).save(any(Payment.class));
		assertEquals(0, pendingPaymentOptional.get().getPendingAmount());
		assertEquals(PaymentMode.CARD, pendingPaymentOptional.get().getPaymentMode());
	}
	
	@Test
	void payByCard_IncorrectDetailsTest() {
		CardDTO fakeCardDTO = new CardDTO();
		fakeCardDTO.setCardNumber(new Random().nextLong());
		fakeCardDTO.setExpiryMonth(new Random().nextInt());
		fakeCardDTO.setExpiryYear(new Random().nextInt());
		fakeCardDTO.setCvv(new Random().nextInt());
		
		Integer courseRegistrationId = 123;
		
		when(cardRepository
				.findByCardNumberAndExpiryMonthAndExpiryYearAndCvv(
						any(Long.class),
						any(Integer.class),
						any(Integer.class),
						any(Integer.class)))
			.thenReturn(Optional.empty());
		
		assertThrows(CardDetailsNotFoundException.class, 
				() -> paymentService.payByCard(fakeCardDTO, courseRegistrationId));
	}
	
	@Test
	void payByNetBankingTest() {
		// Get your inputs ready
		// Already existing payment information
		Optional<Payment> pendingPaymentOptional = Optional.of(new Payment(
				5001,
				201,
				4500,
				4500,
				PaymentMode.CASH));
		
		NetBankingDTO fakeNetBankingDTO = new NetBankingDTO("DummyUser", "dummy@123");
		
		Integer fakeCourseRegistrationId = new Random().nextInt();
		
		// Mock Database calls
		when(netBankingRepository.findByUserNameAndPassword("DummyUser", "dummy@123"))
			.thenReturn(Optional.of(new NetBanking("DummyUser", "dummy@123")));
		
		when(paymentRepository
				.findByCourseRegistrationId(any(Integer.class)))
			.thenReturn(pendingPaymentOptional);

		try {
			paymentService.payByNetBanking(fakeNetBankingDTO, fakeCourseRegistrationId);
		} catch (NetBankingDetailsNotFoundException e) {
			e.printStackTrace();
		}	
		
		verify(paymentRepository, times(1)).save(any(Payment.class));
		assertEquals(0, pendingPaymentOptional.get().getPendingAmount());
		assertEquals(PaymentMode.NETBANKING, pendingPaymentOptional.get().getPaymentMode());
	}
	
	@Test
	void payByNetBanking_IncorrectDetailsTest() {
		NetBankingDTO fakeNetBankingDTO = new NetBankingDTO("DummyUser", "dummy@123");
		
		Integer courseRegistrationId = 123;
		
		when(netBankingRepository
				.findByUserNameAndPassword("DummyUser", "dummy@123"))
			.thenReturn(Optional.empty());
		
		assertThrows(NetBankingDetailsNotFoundException.class, 
				() -> paymentService.payByNetBanking(fakeNetBankingDTO, courseRegistrationId));
	}

	@Test
	void payByUpiTest() {
		// Get your inputs ready
		// Already existing payment information
		Optional<Payment> pendingPaymentOptional = Optional.of(new Payment(
				5001,
				201,
				4500,
				4500,
				PaymentMode.CASH));
		
		UPIDTO fakeUPIDTO = new UPIDTO("dummy@upi");
		
		Integer fakeCourseRegistrationId = new Random().nextInt();
		
		// Mock Database calls
		when(upiRepository.findByUpiId("dummy@upi"))
			.thenReturn(Optional.of(new UPI("dummy@upi")));
		
		when(paymentRepository
				.findByCourseRegistrationId(any(Integer.class)))
			.thenReturn(pendingPaymentOptional);

			try {
				paymentService.payByUPI(fakeUPIDTO, fakeCourseRegistrationId);
			} catch (UPIDetailsNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		verify(paymentRepository, times(1)).save(any(Payment.class));
		assertEquals(0, pendingPaymentOptional.get().getPendingAmount());
		assertEquals(PaymentMode.UPI, pendingPaymentOptional.get().getPaymentMode());
	}
	
	@Test
	void payByUPI_IncorrectDetailsTest() {
		UPIDTO fakeupiDTO = new UPIDTO("DummyUser@upi");
		
		Integer courseRegistrationId = 123;
		
		when(upiRepository.findByUpiId("DummyUser@upi"))
			.thenReturn(Optional.empty());
		
		assertThrows(UPIDetailsNotFoundException.class, 
				() -> paymentService.payByUPI(fakeupiDTO, courseRegistrationId));
	}
}
