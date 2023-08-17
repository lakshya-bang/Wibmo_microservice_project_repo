/**
 * 
 */
package com.wibmo.business;

import java.util.ArrayList;

import com.wibmo.bean.Notification;

/**
 * 
 */
public interface NotificationOperation {
	public void createNotification(Integer forUserId, String message);
	public ArrayList<Notification> retrieveMessages(Integer forUserId);
	public void updateStatus(ArrayList<Notification> notifications);
}
