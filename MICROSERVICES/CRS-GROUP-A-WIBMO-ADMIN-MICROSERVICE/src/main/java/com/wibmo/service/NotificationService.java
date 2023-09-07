/**
 * 
 */
package com.wibmo.service;

import java.util.Set;

import org.springframework.http.ResponseEntity;

/**
 * 
 */
public interface NotificationService {
	
	public ResponseEntity<String> SendApproveOrRejectNotification(String jwt, 
			Set<Integer> courseRegistrationIds, 
			String notificationMessage);
	
	public ResponseEntity SendApproveOrRejectALLNotification (String jwt, 
			String notificationMessage);
}
