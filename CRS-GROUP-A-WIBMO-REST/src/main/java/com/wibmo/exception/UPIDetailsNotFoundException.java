/**
 * 
 */
package com.wibmo.exception;

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
