package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wibmo.entity.Student;

/**
 * 
 */
public interface StudentService {

	/**
	 * @param integer
	 * @return
	 */
	public Student getStudentById(Integer integer);
	
	/**
	 * 
	 * @return
	 */
	public List<Student> getAllStudents();

	/**
	 * 
	 * @param student
	 */
	public void add(Student student);

	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public Boolean isStudentExistsById(Integer studentId);
	
	/**
	 * 
	 * @param studentIds
	 * @return
	 */
	public List<Student> getAllStudentsByIds(Collection<Integer> studentIds);

	/**
	 * 
	 * @param collect
	 * @return
	 */
	public Map<Integer, Student> getStudentIdToStudentMap(Collection<Integer> collect);

}	
