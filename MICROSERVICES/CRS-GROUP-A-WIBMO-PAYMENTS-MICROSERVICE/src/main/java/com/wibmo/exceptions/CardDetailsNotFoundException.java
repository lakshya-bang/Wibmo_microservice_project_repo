/**
 * 
 */
package com.wibmo.exceptions;

/**
 * 
 */
public class CardDetailsNotFoundException extends Exception {

	public CardDetailsNotFoundException() {
	}
	
	@Override
	public String getMessage() {
		return "Invalid Card Payment Details";
	}
}
