/**
 * 
 */
package com.wibmo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.wibmo.enums.NotificationStatus;
import com.wibmo.enums.NotificationType;

/**
 * 
 */
@Entity
@Table(name = "notification")
public class Notification {
	@Id
	@Column(name = "notification_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer notificationId;
	
	@Column(name = "notification_message")
	private String notificationMessage;
	
	@Column(name = "notification_type")
	@NotNull
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
	
	@Column(name = "notification_status")
	@NotNull
	@Enumerated(EnumType.STRING)
	private NotificationStatus notificationStatus = NotificationStatus.PENDING;
	
	@Column(name = "notification_user_id")
	@NotNull
	private Integer notificationUserId;

	/**
	 * @return the notificationUserId
	 */
	public Integer getNotificationUserId() {
		return notificationUserId;
	}

	/**
	 * @param notificationUserId the notificationUserId to set
	 */
	public void setNotificationUserId(Integer notificationUserId) {
		this.notificationUserId = notificationUserId;
	}

	/**
	 * @return the notificationId
	 */
	public Integer getNotificationId() {
		return notificationId;
	}

	/**
	 * @param notificationId the notificationId to set
	 */
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	/**
	 * @return the notificationMessage
	 */
	public String getNotificationMessage() {
		return notificationMessage;
	}

	/**
	 * @param notificationMessage the notificationMessage to set
	 */
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	/**
	 * @return the notificationType
	 */
	public NotificationType getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * @return the notificationStatus
	 */
	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}

	/**
	 * @param notificationStatus the notificationStatus to set
	 */
	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", notificationMessage=" + notificationMessage
				+ ", notificationType=" + notificationType + ", notificationStatus=" + notificationStatus
				+ ", notificationUserId=" + notificationUserId + "]";
	}
	
	
}
