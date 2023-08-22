/**
 * 
 */
package com.wibmo.exception;

/**
 * 
 */
public class ProfessorNotExistsInSystemException extends Exception {

	private Integer professorId;
	
	public ProfessorNotExistsInSystemException(Integer professorId) {
		this.professorId = professorId;
	}
	
	@Override
	public String toString() {
		return "Professor with Id = " + professorId + " does not exists.";
	}
}
