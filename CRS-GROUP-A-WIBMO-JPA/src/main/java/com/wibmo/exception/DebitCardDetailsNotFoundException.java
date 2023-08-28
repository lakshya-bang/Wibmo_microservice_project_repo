/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class DebitCardDetailsNotFoundException extends Exception {

	public DebitCardDetailsNotFoundException() {
	}
	
	@Override
	public String getMessage() {
		return "Invalid Debit Card Payment Details";
	}
}
