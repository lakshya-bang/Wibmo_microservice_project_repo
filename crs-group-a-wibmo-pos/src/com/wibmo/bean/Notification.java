package com.wibmo.bean;

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
   private String  notificationStatus;
   private String notificationMessage;

public Notification(Integer notificationId, Integer notificationUserId, String notificationStatus,
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
public String getNotificationStatus() {
    return notificationStatus;
}
public void setNotificationStatus(String notificationStatus) {
    this.notificationStatus = notificationStatus;
}
public String getNotificationMessage() {
    return notificationMessage;
}
public void setNotificationMessage(String notificationMessage) {
    this.notificationMessage = notificationMessage;
} 
   
}
