/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class UnexpectedTopicException extends Throwable{

	private String topic;
	public UnexpectedTopicException(String topic) {
		this.topic = topic;
	}
	
	@Override
	public String getMessage() {
		return "Topic- " + this.topic + " is not expected.";
	}
}
