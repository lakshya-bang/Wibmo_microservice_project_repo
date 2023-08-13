package com.wibmo.bean;

public class Student {
	
	private Long id;
	private Integer currentSemester;
	
	public Long getId() {
		return id;
	}
	
	public Integer getCurrentSemester() {
		return currentSemester;
	}
	
	public void setCurrentSemester(Integer semester) {
		this.currentSemester = semester;
	}
	
}
