/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class NetBankingDetailsNotFoundException extends Exception {
	public NetBankingDetailsNotFoundException() {
		
	}
	
	@Override
	public String getMessage() {
		return "Invalid Net Banking Payment Details";
	}
}
