/**@author mourila.vatsav
 * 
 */
package com.wibmo.service;

import com.wibmo.dto.PaymentDebitDTO;
import com.wibmo.dto.PaymentNetBankingDTO;
import com.wibmo.dto.PaymentUPIDTO;
import com.wibmo.entity.Bill;
import com.wibmo.enums.PaymentMode;
import com.wibmo.exception.DebitCardDetailsNotFoundException;
import com.wibmo.exception.NetBankingDetailsNotFoundException;
import com.wibmo.exception.UPIDetailsNotFoundException;

/**
 * 
 */
public interface BillingService {
	public Bill viewBillByStudentId(Integer studentId);
	
	public boolean payBillByStudentIdWithDebit(PaymentDebitDTO paymentDebitDTO,Integer studentId) throws DebitCardDetailsNotFoundException;
	public boolean payBillByStudentIdWithNetBanking(PaymentNetBankingDTO paymentNetBankingDTO,Integer studentId) throws NetBankingDetailsNotFoundException;

	public boolean payBillByStudentIdWithUPI(PaymentUPIDTO paymentUPI,Integer studentId) throws UPIDetailsNotFoundException;

}
