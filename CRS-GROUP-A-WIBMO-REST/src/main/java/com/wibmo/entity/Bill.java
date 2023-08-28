/**
 * 
 */
package com.wibmo.entity;

/**
 * 
 */
public class Bill {
	private Integer billId;
	private Integer studentId;
	private Integer courseRegId;
	private Integer totalAmount;
	private Integer amountPending;
	/**
	 * @return the billId
	 */
	public Integer getBillId() {
		return billId;
	}
	/**
	 * @param billId the billId to set
	 */
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	/**
	 * @return the studentId
	 */
	public Integer getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the courseRegId
	 */
	public Integer getCourseRegId() {
		return courseRegId;
	}
	/**
	 * @param courseRegId the courseRegId to set
	 */
	public void setCourseRegId(Integer courseRegId) {
		this.courseRegId = courseRegId;
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
	 * @return the amountPending
	 */
	public Integer getAmountPending() {
		return amountPending;
	}
	/**
	 * @param amountPending the amountPending to set
	 */
	public void setAmountPending(Integer amountPending) {
		this.amountPending = amountPending;
	}
}
