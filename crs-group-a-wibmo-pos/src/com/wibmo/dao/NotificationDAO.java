/**
 * 
 */
package com.wibmo.dao;

import java.util.ArrayList;

import com.wibmo.bean.Notification;

/**
 * @author lakshya.bang
 */
public interface NotificationDAO {
	/**
	 * 
	 * @param notification
	 */
	public void create(Notification notification);
	/**
	 * 
	 * @param userId Integer
	 * @return
	 */
	public ArrayList<Notification> fetchByUserId(Integer userId);
	/**
	 * 
	 * @param notifications ArrayList<Notification>
	 */
	public void updateStatus(ArrayList<Notification> notifications);

	public ArrayList<Notification> fetchAllById(Integer userId);
}
