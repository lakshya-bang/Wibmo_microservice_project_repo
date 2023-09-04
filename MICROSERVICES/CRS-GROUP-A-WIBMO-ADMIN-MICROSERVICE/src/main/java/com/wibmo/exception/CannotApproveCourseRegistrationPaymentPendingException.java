package com.wibmo.exception;

/**
 * 
 */
public class CannotApproveCourseRegistrationPaymentPendingException extends Exception {

	private Integer courseRegistrationId;
	
	public CannotApproveCourseRegistrationPaymentPendingException(Integer courseRegistrationId) {
		this.courseRegistrationId = courseRegistrationId;
	}
	
	@Override
	public String getMessage() {
		return "Course Registration Id: " + courseRegistrationId + " CANNOT be Approved due to Pending Payment.";
	}
}
