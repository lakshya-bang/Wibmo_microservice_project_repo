/**@author mourila.vatsav
 * 
 */
package com.wibmo.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dto.CardDTO;
import com.wibmo.dto.NetBankingDTO;
import com.wibmo.dto.UPIDTO;
import com.wibmo.entity.Payment;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.exception.CardDetailsNotFoundException;
import com.wibmo.exception.NetBankingDetailsNotFoundException;
import com.wibmo.exception.UPIDetailsNotFoundException;
import com.wibmo.repository.PaymentRepository;

/**
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private UPIServiceImpl upiService;
	
	@Autowired
	private NetBankingServiceImpl netBankingService;
	
	@Autowired
	private CardServiceImpl cardService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	private static final Logger LOG = LogManager.getLogger(PaymentServiceImpl.class);
	
	@Override
	public void add(Payment payment) {
		
		if(null == payment) {
			return;
		}
		
		paymentRepository.save(payment);
	}
	
	@Override
	public Payment getPaymentByCourseRegistrationId(Integer courseRegistrationId) {
		
		if(null == courseRegistrationId) {
			return null;
		}
		
		return paymentRepository
				.findByCourseRegistrationId(courseRegistrationId)
				.map(payment -> payment)
				.orElse(null);
	}

	@Override
	public Boolean approveCashPayment(Integer courseRegistrationId) {
		
		return pay(PaymentMode.CASH, courseRegistrationId);
		
	}

	@Override
	public Boolean payByCard(CardDTO cardDTO,Integer courseRegistrationId) 
			throws CardDetailsNotFoundException {
		
		Boolean verificationStatus = cardService.verfiy(cardDTO);
		
		if(Boolean.FALSE.equals(verificationStatus)) {
			 throw new CardDetailsNotFoundException();
		}
		
		return pay(PaymentMode.CARD, courseRegistrationId);
	}


	@Override
	public Boolean payByNetBanking(
			NetBankingDTO netBankingDTO, Integer courseRegistrationId) 
					throws NetBankingDetailsNotFoundException {

		Boolean verificationStatus = netBankingService.verify(netBankingDTO);
		
		if(Boolean.FALSE.equals(verificationStatus)) {
			throw new NetBankingDetailsNotFoundException();
		}
		
		return pay(PaymentMode.NETBANKING, courseRegistrationId);
	}


	@Override
	public Boolean payByUPI(
			UPIDTO upiDTO, Integer courseRegistrationId) 
					throws UPIDetailsNotFoundException {

		Boolean verificationStatus = upiService.verify(upiDTO);
		
		if(Boolean.FALSE.equals(verificationStatus)) {
			throw new UPIDetailsNotFoundException();
		}
		
		return pay(PaymentMode.UPI, courseRegistrationId);
	}

	/**
	 * 
	 * @param courseRegistrationId
	 * @return
	 */
	private Boolean pay(PaymentMode paymentMode, Integer courseRegistrationId) {
		
		LOG.info("paymentMode: " + paymentMode);
		
		Optional<Payment> paymentOptional = paymentRepository
				.findByCourseRegistrationId(courseRegistrationId);
		
		if(!paymentOptional.isPresent()) {
			return Boolean.FALSE;
		}
		
		Payment payment = paymentOptional.get();
		
		payment.setPendingAmount(0);
		payment.setPaymentMode(paymentMode);
		
		paymentRepository.save(payment);
		
		return Boolean.TRUE;
	}

	@Override
	public PaymentStatus getPaymentStatusByCourseRegistrationId(Integer courseRegistrationId) {
		return paymentRepository
			.findByCourseRegistrationId(courseRegistrationId)
			.map(payment -> payment.getPendingAmount() == 0 
				? PaymentStatus.PAID : PaymentStatus.UNPAID)
			.orElse(null);
	}
}
