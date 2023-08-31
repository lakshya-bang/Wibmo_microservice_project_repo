package com.wibmo.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wibmo.entity.Student;
import com.wibmo.enums.RegistrationStatus;
import com.wibmo.enums.UserType;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Student getStudentById(Integer studentId) throws UserNotFoundException {
		if(!studentRepository.existsByStudentId(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		Optional<Student> studentOptional = studentRepository.findByStudentId(studentId);
		return studentOptional.isPresent()
				? studentOptional.get()
				: null;
	}
	
	@Override
	public List<Student> getAllStudents() {
		//TODO: empty exception
		return studentRepository.findAll();
	}
	
	@Override
	public List<Student> getAllStudentsByIds(Collection<Integer> studentIds) {
		//TODO: check if all studentIds Exists
		return studentRepository.findAllByStudentIdIn(studentIds);
	}

	@Override
	public void add(Student student) {
		if(null == student) {
			return;
		}
		studentRepository.save(student);
	}
	
	@Override
	public Boolean isStudentExistsById(Integer studentId) throws UserNotFoundException {
		if(!studentRepository.existsByStudentId(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		return studentRepository
				.findByStudentId(studentId)
				.isPresent();
	}
	//To fill the DTO and avoid looping multiple times on student id in registered_course
//	table to get student details we are using Map instead of List
	@Override
	public Map<Integer, Student> getStudentIdToStudentMap(Collection<Integer> studentIds) {
		//TODO: add studentIds check
		return studentRepository
				.findAllByStudentIdIn(studentIds)
				.stream()
				.collect(Collectors.toMap(
						Student::getStudentId,
						Function.identity()));
	}
	
	@Override
	public Integer getCurrentSemesterByStudentId(Integer studentId) throws UserNotFoundException {
		if(!studentRepository.existsByStudentId(studentId)) {
			throw new UserNotFoundException(studentId, UserType.STUDENT);
		}
		return studentRepository
				.findByStudentId(studentId)
				.get()
				.getCurrentSemester();
	}
	
	/***************************** Utility methods ********************************/
	
	private Integer getUserId(Integer studentId) {
		return studentRepository
				.findById(studentId)
				.get()
				.getUserId();
	}
	
}
