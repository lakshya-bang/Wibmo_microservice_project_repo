/**@author mourila.vatsav
 * 
 */
package com.wibmo.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.wibmo.dto.CardDTO;
import com.wibmo.dto.NetBankingDTO;
import com.wibmo.dto.UPIDTO;
import com.wibmo.entity.Card;
import com.wibmo.entity.NetBanking;
import com.wibmo.entity.Payment;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.exceptions.CardDetailsNotFoundException;
import com.wibmo.exceptions.NetBankingDetailsNotFoundException;
import com.wibmo.exceptions.UPIDetailsNotFoundException;
import com.wibmo.repository.CardRepository;
import com.wibmo.repository.NetBankingRepository;
import com.wibmo.repository.PaymentRepository;
import com.wibmo.repository.UPIRepository;

/**
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private NetBankingRepository netBankingRepository;
	
	@Autowired
	private UPIRepository upiRepository;
	
	private static final Logger LOG = LogManager.getLogger(PaymentServiceImpl.class);
	
	@Override
	public void add(Payment payment) {
		
		if(null == payment) {
			return;
		}
		
		paymentRepository.save(payment);
	}
	
	@Override
	@Cacheable(value="Payment_payment")
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
	@CachePut(value="Payment_approve",key="#courseRegistrationId")
	public Boolean approveCashPayment(Integer courseRegistrationId) {
		
		return pay(PaymentMode.CASH, courseRegistrationId);
		
	}

	@Override
	public Boolean payByCard(CardDTO cardDTO,Integer courseRegistrationId) 
			throws CardDetailsNotFoundException {
		
		Boolean verificationStatus = verfiy(cardDTO);
		
		if(Boolean.FALSE.equals(verificationStatus)) {
			 throw new CardDetailsNotFoundException();
		}
		
		return pay(PaymentMode.CARD, courseRegistrationId);
	}


	@Override
	public Boolean payByNetBanking(
			NetBankingDTO netBankingDTO, Integer courseRegistrationId) 
					throws NetBankingDetailsNotFoundException {

		Boolean verificationStatus = verify(netBankingDTO);
		
		if(Boolean.FALSE.equals(verificationStatus)) {
			throw new NetBankingDetailsNotFoundException();
		}
		
		return pay(PaymentMode.NETBANKING, courseRegistrationId);
	}


	@Override
	public Boolean payByUPI(
			UPIDTO upiDTO, Integer courseRegistrationId) 
					throws UPIDetailsNotFoundException {

		Boolean verificationStatus = verify(upiDTO);
		
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
		
		// TODO: payment optional is false as courseRegistration service is different.
		Optional<Payment> paymentOptional = paymentRepository
				.findByCourseRegistrationId(courseRegistrationId);
		
		if(!paymentOptional.isPresent()) {
			return Boolean.FALSE;
		}
		//
		
		Payment payment = paymentOptional.get();
		
		payment.setPendingAmount(0);
		payment.setPaymentMode(paymentMode);
		
		paymentRepository.save(payment);
		
		return Boolean.TRUE;
	}

	@Override
	@Cacheable(value="Payment_paymentStatus")
	public PaymentStatus getPaymentStatusByCourseRegistrationId(Integer courseRegistrationId) {
		return paymentRepository
			.findByCourseRegistrationId(courseRegistrationId)
			.map(payment -> payment.getPendingAmount() == 0 
				? PaymentStatus.PAID : PaymentStatus.UNPAID)
			.orElse(null);
	}
	
	/**
	 * card Repository services integrated
	 */
	@Override
	@Cacheable(value="Payment_verify_card")
	public Boolean verfiy(CardDTO cardDTO) {
		Optional<Card> cardOptional = cardRepository
				.findByCardNumberAndExpiryMonthAndExpiryYearAndCvv(
						cardDTO.getCardNumber(), 
						cardDTO.getExpiryMonth(), 
						cardDTO.getExpiryYear(), 
						cardDTO.getCvv());
		return cardOptional.isPresent();
	}
	
	/**
	 * Net-banking Repository Services integrated
	 */
	@Override
	@Cacheable(value="Payment_verify_netBanking")
	public Boolean verify(NetBankingDTO netBankingDTO) {
		Optional<NetBanking> netBankingOptional = 
				netBankingRepository.findByUserNameAndPassword(
						netBankingDTO.getUserName(), 
						netBankingDTO.getPassword());
		return netBankingOptional.isPresent();
	}
	
	/**
	 * UPI repository Services integrated
	 */
	@Override
	@Cacheable(value="Payment_verify_upi")
	public Boolean verify(UPIDTO upiDTO) {
		return upiRepository
				.findByUpiId(upiDTO.getUpiId())
				.isPresent();
	}
	
}
