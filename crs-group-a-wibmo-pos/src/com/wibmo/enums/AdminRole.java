package com.wibmo.enums;

import java.util.Set;

/**
 * Defines the roles that are assigned to Admins
 * 
 * @author abhishek.sharma
 */
public enum AdminRole {
	ADD_COURSE_TO_CATALOG,
	DROP_COURSE_FROM_CATALOG,
	APPROVE_REJECT_STUDENT_REGISTRATION;
	
	public static final Set<AdminRole> UPDATE_COURSE_CATALOG = Set.of(
			ADD_COURSE_TO_CATALOG, 
			DROP_COURSE_FROM_CATALOG);
	
}
