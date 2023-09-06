package com.wibmo.service;

import java.util.List;

import com.wibmo.entity.*;

public interface NotificationService {

	public List<Notification> viewNotification(String jwt);
}
