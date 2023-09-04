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
import com.wibmo.exceptions.CardDetailsNotFoundException;
import com.wibmo.exceptions.NetBankingDetailsNotFoundException;
import com.wibmo.exceptions.UPIDetailsNotFoundException;


/**
 * 
 */
public interface PaymentService {
	
	/**
	 * This Method gets payment bill using course registration id
	 * @param studentId
	 * @return
	 */
	public Payment getPaymentByCourseRegistrationId(Integer courseRegistrationId);
	
	/**
	 * This Method is to pay after course registration by Card
	 * @param cardDTO
	 * @param courseRegistrationId
	 * @return
	 * @throws CardDetailsNotFoundException
	 */
	public Boolean payByCard(CardDTO cardDTO, Integer courseRegistrationId) 
		throws CardDetailsNotFoundException;
	
	/**
	 * This Method is to pay after course registration by Net Banking
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
	 * This Method is to pay after course registration by upi
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
	 * This Method adds the payments details to database
	 * @param payment
	 */
	public void add(Payment payment);

	/**
	 * This Method gets payment status using course registration id
	 * @param courseRegistrationId
	 * @return
	 */
	public PaymentStatus getPaymentStatusByCourseRegistrationId(Integer courseRegistrationId);

	/**
	 * This Method approves the cash Payment
	 * @param courseRegistrationId
	 * @return
	 */
	public Boolean approveCashPayment(Integer courseRegistrationId);

	/**
	 * This Method verifies Card details
	 * @param cardDTO
	 * @return
	 */
	Boolean verfiy(CardDTO cardDTO);

	/**
	 * This Method verifies Net Banking details
	 * @param netBankingDTO
	 * @return
	 */
	Boolean verify(NetBankingDTO netBankingDTO);
	
	/**
	 * This Method verifies Upi details
	 * @param upiDTO
	 * @return
	 */
	Boolean verify(UPIDTO upiDTO);

}
