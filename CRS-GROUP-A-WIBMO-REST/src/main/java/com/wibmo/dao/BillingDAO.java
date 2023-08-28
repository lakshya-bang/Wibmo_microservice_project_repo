/**@author mourila.vatsav
 * 
 */
package com.wibmo.dao;

import com.wibmo.dto.PaymentDebitDTO;
import com.wibmo.dto.PaymentNetBankingDTO;
import com.wibmo.dto.PaymentUPIDTO;
import com.wibmo.entity.Bill;

/**
 * 
 */
public interface BillingDAO {
	public Bill findByStudentId(Integer studentId);
	public boolean verifyDebitDetails(PaymentDebitDTO paymentDebitDTO,Integer studentId);
	public boolean verifyNetBankingDetails(PaymentNetBankingDTO paymentNetBankingDTO,Integer studentId);
	public boolean verifyUPIDetails(PaymentUPIDTO paymentUPIDTO,Integer studentId);
	public boolean payBill(Integer studentId);
}
