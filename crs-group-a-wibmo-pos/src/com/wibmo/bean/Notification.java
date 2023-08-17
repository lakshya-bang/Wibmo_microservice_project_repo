package com.wibmo.bean;

import com.wibmo.enums.NotificationStatus;

public class Notification {
    /*
     * DB - 
     * notificationId == notification_id
     * notificationUserId == notification_to_user
     * notificationStatus == notification_status
     * notificationMessage == notification_message
     */
   private Integer notificationId;      
   private Integer notificationUserId;
   private NotificationStatus  notificationStatus;
   private String notificationMessage;

public Notification(Integer notificationId, Integer notificationUserId, NotificationStatus notificationStatus,
        String notificationMessage) {
    this.notificationId = notificationId;
    this.notificationUserId = notificationUserId;
    this.notificationStatus = notificationStatus;
    this.notificationMessage = notificationMessage;
}
public Notification() {
}
public Integer getNotificationId() {
    return notificationId;
}
public void setNotificationId(Integer notificationId) {
    this.notificationId = notificationId;
}
public Integer getNotificationUserId() {
    return notificationUserId;
}
public void setNotificationUserId(Integer notificationUserId) {
    this.notificationUserId = notificationUserId;
}
public NotificationStatus getNotificationStatus() {
    return notificationStatus;
}
public void setNotificationStatus(NotificationStatus notificationStatus) {
    this.notificationStatus = notificationStatus;
}
public String getNotificationMessage() {
    return notificationMessage;
}
public void setNotificationMessage(String notificationMessage) {
    this.notificationMessage = notificationMessage;
} 
   
}
