/**
 * 
 */
package com.wibmo.exceptions;

/**
 * 
 */
public class UPIDetailsNotFoundException extends Exception{
	public UPIDetailsNotFoundException() {
		
	}
	@Override
	public String getMessage() {
		return "Invalid UPI Payment Details";
	}
}
