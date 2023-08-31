/**@author mourila.vatsav
 * 
 */
package com.wibmo.service;

import com.wibmo.dto.CardDTO;
import com.wibmo.dto.NetBankingDTO;
import com.wibmo.dto.UPIDTO;
import com.wibmo.entity.Payment;
import com.wibmo.enums.PaymentMode;
import com.wibmo.enums.PaymentStatus;
import com.wibmo.exception.CardDetailsNotFoundException;
import com.wibmo.exception.NetBankingDetailsNotFoundException;
import com.wibmo.exception.UPIDetailsNotFoundException;

/**
 * 
 */
public interface PaymentService {
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public Payment getPaymentByCourseRegistrationId(Integer courseRegistrationId);
	
	/**
	 * 
	 * @param cardDTO
	 * @param courseRegistrationId
	 * @return
	 * @throws CardDetailsNotFoundException
	 */
	public Boolean payByCard(CardDTO cardDTO, Integer courseRegistrationId) 
		throws CardDetailsNotFoundException;
	
	/**
	 * 
	 * @param paymentNetBankingDTO
	 * @param courseRegistrationId
	 * @return
	 * @throws NetBankingDetailsNotFoundException
	 */
	public Boolean payByNetBanking(
			NetBankingDTO netBankingDTO,
			Integer studentId) 
		throws NetBankingDetailsNotFoundException;

	/**
	 * 
	 * @param upiDTO
	 * @param courseRegistrationId
	 * @return
	 * @throws UPIDetailsNotFoundException
	 */
	public Boolean payByUPI(
			UPIDTO upiDTO,
			Integer courseRegistrationId) 
		throws UPIDetailsNotFoundException;

	/**
	 * 
	 * @param payment
	 */
	public void add(Payment payment);

	/**
	 * 
	 * @param courseRegistrationId
	 * @return
	 */
	public PaymentStatus getPaymentStatusByCourseRegistrationId(Integer courseRegistrationId);

	/**
	 * 
	 * @param courseRegistrationId
	 * @return
	 */
	public Boolean approveCashPayment(Integer courseRegistrationId);

}
