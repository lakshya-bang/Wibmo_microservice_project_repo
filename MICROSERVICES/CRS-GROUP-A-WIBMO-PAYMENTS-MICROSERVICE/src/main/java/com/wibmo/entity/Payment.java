/**
 * 
 */
package com.wibmo.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wibmo.enums.PaymentMode;

/**
 * 
 */
@Entity
@Table(name = "payment")
public class Payment {
	
	@Id
	@Column(name = "payment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;
	
	@Column(name = "course_registration_id")
	private Integer courseRegistrationId;
	
	@Column(name = "total_amount")
	private Integer totalAmount;
	
	@Column(name = "pending_amount")
	private Integer pendingAmount;
	
	@Column(name = "payment_mode")
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;
	
	/**
	 * @return the paymentId
	 */
	public Integer getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	/**
	 * @return the courseRegistrationId
	 */
	public Integer getCourseRegistrationId() {
		return courseRegistrationId;
	}
	/**
	 * @param courseRegId the courseRegId to set
	 */
	public void setCourseRegistrationId(Integer courseRegistrationId) {
		this.courseRegistrationId = courseRegistrationId;
	}
	/**
	 * @return the totalAmount
	 */
	public Integer getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return the pendingAmount
	 */
	public Integer getPendingAmount() {
		return pendingAmount;
	}
	/**
	 * @param pendingAmount the amountPending to set
	 */
	public void setPendingAmount(Integer pendingAmount) {
		this.pendingAmount = pendingAmount;
	}
	/**
	 * @return the paymentMode
	 */
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", courseRegistrationId=" + courseRegistrationId + ", totalAmount="
				+ totalAmount + ", pendingAmount=" + pendingAmount + ", paymentMode=" + paymentMode + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(courseRegistrationId, paymentId, paymentMode, pendingAmount, totalAmount);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(courseRegistrationId, other.courseRegistrationId)
				&& Objects.equals(paymentId, other.paymentId) && paymentMode == other.paymentMode
				&& Objects.equals(pendingAmount, other.pendingAmount) && Objects.equals(totalAmount, other.totalAmount);
	}

	
}
