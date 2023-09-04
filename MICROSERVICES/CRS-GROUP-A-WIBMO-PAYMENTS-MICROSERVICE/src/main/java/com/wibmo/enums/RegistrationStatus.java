package com.wibmo.enums;

import java.util.Set;

/**
 * Defines the statuses a Student Registration can be at a time.
 * 
 * @author abhishek.sharma
 */
public enum RegistrationStatus {
	APPROVED,
	REJECTED,
	PENDING;
	
	public static final Set<RegistrationStatus> 
		INVALID_REGISTRATION_STATUSES = Set.of(PENDING, REJECTED);
}
