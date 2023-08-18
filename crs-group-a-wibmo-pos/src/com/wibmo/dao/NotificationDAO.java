/**
 * 
 */
package com.wibmo.dao;

import java.util.ArrayList;

import com.wibmo.bean.Notification;

/**
 * 
 */
public interface NotificationDAO {
	public void create(Notification notification);
	public ArrayList<Notification> fetchByUserId(Integer userId);
	public void updateStatus(ArrayList<Notification> notifications);
}
