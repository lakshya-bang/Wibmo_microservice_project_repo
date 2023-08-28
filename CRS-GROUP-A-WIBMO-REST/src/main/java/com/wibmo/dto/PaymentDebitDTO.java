/**
 * 
 */
package com.wibmo.dto;

/**
 * 
 */
public class PaymentDebitDTO {
	private String debitAccountNumber;
	private String debitExpiryDate;
	private Integer debitCvv;

	/**
	 * @return the debitAccountNumber
	 */
	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}
	/**
	 * @param debitAccountNumber the debitAccountNumber to set
	 */
	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}
	/**
	 * @return the debitExpiryDate
	 */
	public String getDebitExpiryDate() {
		return debitExpiryDate;
	}
	/**
	 * @param debitExpiryDate the debitExpiryDate to set
	 */
	public void setDebitExpiryDate(String debitExpiryDate) {
		this.debitExpiryDate = debitExpiryDate;
	}
	/**
	 * @return the debitCvv
	 */
	public Integer getDebitCvv() {
		return debitCvv;
	}
	/**
	 * @param debitCvv the debitCvv to set
	 */
	public void setDebitCvv(Integer debitCvv) {
		this.debitCvv = debitCvv;
	}
}
