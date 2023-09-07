/**
 * 
 */
package com.wibmo.service;

import org.springframework.http.ResponseEntity;

import com.wibmo.entity.Notification;

/**
 * 
 */
public interface NotificationService {
	public ResponseEntity<String> SendPaymentNotification(String jwt);
}
