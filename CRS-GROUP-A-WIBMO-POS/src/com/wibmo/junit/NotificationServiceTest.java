/**
 * 
 */
package com.wibmo.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wibmo.bean.Notification;
import com.wibmo.business.NotificationOperation;
import com.wibmo.business.NotificationOperationImpl;
import com.wibmo.dao.NotificationDAO;
import com.wibmo.dao.NotificationDAOImpl;
import com.wibmo.enums.NotificationStatus;

/**
 * @author lakshya.bang
 */
public class NotificationServiceTest {
	private NotificationOperation notificationService;
	private Integer userId = 0;
	private String message = "Test Message";
	private NotificationDAO notificationDao;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		notificationService = new NotificationOperationImpl();
		notificationService.createNotification(userId, message);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createNotificationTest() {
		assertFalse(notificationService.retrieveMessages(userId).isEmpty());
	}
	
	@Test
	public void retrieveMessageTest() {
		assertEquals(message,notificationService.retrieveMessages(userId).get(0).getNotificationMessage());
	}
	
	@Test
	public void updateStatusTest(){
		ArrayList<Notification> notifications = notificationService.fetchAllNotificationsByUserId(userId);
		assertTrue(notifications.stream().anyMatch(obj ->
	    obj.getNotificationStatus() == NotificationStatus.PENDING ));
		notificationService.updateStatus(notifications);
		notifications = notificationService.fetchAllNotificationsByUserId(userId);
		assertFalse(notifications.stream().anyMatch(obj ->
	    obj.getNotificationStatus() == NotificationStatus.PENDING ));
	}
}
