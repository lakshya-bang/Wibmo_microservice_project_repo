/**@author mourila.vatsav
 * 
 */
package com.wibmo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.dao.BillingDAOImpl;
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
@Service
public class BillingServiceImpl implements BillingService{
	
	@Autowired
	BillingDAOImpl billingDAO;
	
	
	@Override
	public Bill viewBillByStudentId(Integer studentId) {
		// TODO Auto-generated method stub
		
		return billingDAO.findByStudentId(studentId);
	}


	@Override
	public boolean payBillByStudentIdWithDebit(PaymentDebitDTO paymentDebitDTO,Integer studentId) throws DebitCardDetailsNotFoundException {
		// TODO Auto-generated method stub
		boolean verificationStatus =billingDAO.verifyDebitDetails(paymentDebitDTO,studentId); 
		 if(verificationStatus==false) {
			 throw new DebitCardDetailsNotFoundException();
		 }
		 return billingDAO.payBill(studentId);
	}


	@Override
	public boolean payBillByStudentIdWithNetBanking(PaymentNetBankingDTO paymentNetBankingDTO, Integer studentId) throws NetBankingDetailsNotFoundException {
		// TODO Auto-generated method stub
		boolean verificationStatus = billingDAO.verifyNetBankingDetails(paymentNetBankingDTO, studentId);
		if(verificationStatus) {
			throw new NetBankingDetailsNotFoundException();
		}
		return billingDAO.payBill(studentId); 
	}


	@Override
	public boolean payBillByStudentIdWithUPI(PaymentUPIDTO paymentUPI, Integer studentId) throws UPIDetailsNotFoundException {
		// TODO Auto-generated method stub
		boolean verificationStatus = billingDAO.verifyUPIDetails(paymentUPI, studentId);
		if(verificationStatus) {
			throw new UPIDetailsNotFoundException();
		}
		return billingDAO.payBill(studentId);
	}

}
