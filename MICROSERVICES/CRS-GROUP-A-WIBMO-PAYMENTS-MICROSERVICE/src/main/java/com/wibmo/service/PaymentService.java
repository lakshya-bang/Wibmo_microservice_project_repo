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
	 * @param courseRegistrationId id of course registration of a student to get payment 
	 * @return Payment object
	 */
	public Payment getPaymentByCourseRegistrationId(Integer courseRegistrationId);
	
	/**
	 * This Method is to pay after course registration by Card
	 * @param cardDTO card details of a user
	 * @param courseRegistrationId id of course registration of a student
	 * @return boolean true or false 
	 * @throws CardDetailsNotFoundException throws eception when wrong card details are entered or if they don't exist
	 */
	public Boolean payByCard(CardDTO cardDTO, Integer courseRegistrationId) 
		throws CardDetailsNotFoundException;
	
	/**
	 * This Method is to pay after course registration by Net Banking
	 * @param netBankingDTO net banking details of a user
	 * @param courseRegistrationId id of course registration of a student
	 * @return boolean true or false
	 * @throws NetBankingDetailsNotFoundException throws eception when wrong netbanking details are entered or if they don't exist
	 */
	public Boolean payByNetBanking(
			NetBankingDTO netBankingDTO,
			Integer courseRegistrationId) 
		throws NetBankingDetailsNotFoundException;

	/**
	 * This Method is to pay after course registration by upi
	 * @param upiDTO upi details details of a user
	 * @param courseRegistrationId id of course registration of a student
	 * @return boolean true or false
	 * @throws UPIDetailsNotFoundException throws eception when wrong upi details are entered or if they don't exist
	 */
	public Boolean payByUPI(
			UPIDTO upiDTO,
			Integer courseRegistrationId) 
		throws UPIDetailsNotFoundException;

	/**
	 * This Method adds the payments details to database
	 * @param payment payment object containing details of a user's payment and amount
	 */
	public void add(Payment payment);

	/**
	 * This Method gets payment status using course registration id
	 * @param courseRegistrationId id of course registration of a student
	 * @return PaymentStatus status paid or not
	 */
	public PaymentStatus getPaymentStatusByCourseRegistrationId(Integer courseRegistrationId);

	/**
	 * This Method approves the cash Payment
	 * @param courseRegistrationId id of course registration of a student
	 * @return boolean true or false
	 */
	public Boolean approveCashPayment(Integer courseRegistrationId);

	/**
	 * This Method verifies Card details
	 * @param cardDTO card details details of a user
	 * @return boolean true or false
	 */
	Boolean verfiy(CardDTO cardDTO);

	/**
	 * This Method verifies Net Banking details
	 * @param netBankingDTO netBanking details details of a user
	 * @return boolean true or false
	 */
	Boolean verify(NetBankingDTO netBankingDTO);
	
	/**
	 * This Method verifies Upi details
	 * @param upiDTO upi netBanking
	 * @return boolean true or false
	 */
	Boolean verify(UPIDTO upiDTO);

}
