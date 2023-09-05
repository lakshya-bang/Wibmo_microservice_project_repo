/**
 * 
 */
package com.wibmo.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wibmo.entity.Student;
import com.wibmo.repository.StudentRepository;

public class TestStudentUtils {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentUtils studentUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsStudentExistsById_StudentExists_ReturnsTrue() {
        Integer studentId = 1;
        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(new Student()));

        Boolean result = studentUtils.isStudentExistsById(studentId);

        assertTrue(result);
    }

    @Test
    public void testIsStudentExistsById_StudentDoesNotExist_ReturnsFalse() {
        Integer studentId = 1;
        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.empty());

        Boolean result = studentUtils.isStudentExistsById(studentId);

        assertFalse(result);
    }

    @Test
    public void testGetCurrentSemesterByStudentId_StudentExists_ReturnsCurrentSemester() {
        Integer studentId = 1;
        Integer currentSemester = 3;
        when(studentRepository.findByStudentId(studentId)).thenReturn(Optional.of(new Student(studentId,null,null,null,currentSemester)));

        Integer result = studentUtils.getCurrentSemesterByStudentId(studentId);

        assertEquals(currentSemester, result);
    }

 
}
