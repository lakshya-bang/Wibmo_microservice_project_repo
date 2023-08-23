/**
 * 
 */
package com.wibmo.junit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wibmo.application.CrsGroupAWibmoRestApplication;

//import com.wibmo.bean.Notification;
//import com.wibmo.business.NotificationOperation;
//import com.wibmo.business.NotificationOperationImpl;
//import com.wibmo.dao.NotificationDAO;
//import com.wibmo.dao.NotificationDAOImpl;
//import com.wibmo.enums.NotificationStatus;

/**
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CrsGroupAWibmoRestApplication.class})
class NotificationServiceTest {
	
//	private NotificationOperation notificationService;
//	private Integer userId = 0;
//	private String message = "Test Message";
//	private NotificationDAO notificationDao;
//
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeEach
//	void setUp() throws Exception {
//		notificationService = new NotificationOperationImpl();
//		notificationService.createNotification(userId, message);
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterEach
//	void tearDown() throws Exception {
//	}
//
//	@Test
//	public void createNotificationTest() {
//		assertFalse(notificationService.retrieveMessages(userId).isEmpty());
//	}
//	
//	@Test
//	public void retrieveMessageTest() {
//		assertEquals(message,notificationService.retrieveMessages(userId).get(0).getNotificationMessage());
//	}
//	
//	@Test
//	public void updateStatusTest(){
//		ArrayList<Notification> notifications = notificationService.fetchAllNotificationsByUserId(userId);
//		assertTrue(notifications.stream().anyMatch(obj ->
//	    obj.getNotificationStatus() == NotificationStatus.PENDING ));
//		notificationService.updateStatus(notifications);
//		notifications = notificationService.fetchAllNotificationsByUserId(userId);
//		assertFalse(notifications.stream().anyMatch(obj ->
//	    obj.getNotificationStatus() == NotificationStatus.PENDING ));
//	}

}
