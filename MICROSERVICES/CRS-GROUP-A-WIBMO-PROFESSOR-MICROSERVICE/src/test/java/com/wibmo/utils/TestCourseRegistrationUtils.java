/**
 * 
 */
package com.wibmo.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wibmo.entity.CourseRegistration;
import com.wibmo.enums.CourseType;
import com.wibmo.exception.CourseNotExistsInCatalogException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.CourseRegistrationRepository;

public class TestCourseRegistrationUtils {

    @Mock
    private CourseRegistrationRepository courseRegistrationRepository;

    @Mock
    private CourseUtils courseUtils;

    @Mock
    private StudentUtils studentUtils;

    @InjectMocks
    private CourseRegistrationUtils courseRegistrationUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCourseRegistrationByStudentIdAndSemester_RegistrationExists_ReturnsCourseRegistration() {
        Integer studentId = 1;
        Integer semester = 1;
        CourseRegistration expectedRegistration = new CourseRegistration();

        when(courseRegistrationRepository.findByStudentIdAndSemester(studentId, semester)).thenReturn(Optional.of(expectedRegistration));

        CourseRegistration result = courseRegistrationUtils.getCourseRegistrationByStudentIdAndSemester(studentId, semester);

        assertEquals(expectedRegistration, result);
    }

    @Test
    public void testGetCourseRegistrationByStudentIdAndSemester_RegistrationDoesNotExist_ReturnsNull() {
        Integer studentId = 1;
        Integer semester = 1;

        when(courseRegistrationRepository.findByStudentIdAndSemester(studentId, semester)).thenReturn(Optional.empty());

        CourseRegistration result = courseRegistrationUtils.getCourseRegistrationByStudentIdAndSemester(studentId, semester);

        assertNull(result);
    }

    @Test
    public void testHasRegistrationByStudentIdAndCourseId_StudentDoesNotExist_ThrowsUserNotFoundException() {
        Integer studentId = 1;
        Integer courseId = 1;

        when(studentUtils.isStudentExistsById(studentId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            courseRegistrationUtils.hasRegistrationByStudentIdAndCourseId(studentId, courseId);
        });
    }

    @Test
    public void testHasRegistrationByStudentIdAndCourseId_CourseDoesNotExist_ThrowsCourseNotExistsInCatalogException() {
        Integer studentId = 1;
        Integer courseId = 1;

        when(studentUtils.isStudentExistsById(studentId)).thenReturn(true);
        when(courseUtils.isCourseExistsInCatalog(courseId)).thenReturn(false);

        assertThrows(CourseNotExistsInCatalogException.class, () -> {
            courseRegistrationUtils.hasRegistrationByStudentIdAndCourseId(studentId, courseId);
        });
    }

    @Test
    public void testHasRegistrationByStudentIdAndCourseId_CourseTypeIsAlternative_ReturnsTrue() throws UserNotFoundException, CourseNotExistsInCatalogException {
        Integer studentId = 1;
        Integer courseId = 1;
        CourseType courseType = CourseType.ALTERNATIVE;

        when(studentUtils.isStudentExistsById(studentId)).thenReturn(true);
        when(courseUtils.isCourseExistsInCatalog(courseId)).thenReturn(true);
        when(courseUtils.getCourseTypeByCourseId(courseId)).thenReturn(courseType);
        when(courseRegistrationRepository.existsByStudentIdAndAlternativeCourse1Id(studentId, courseId)).thenReturn(true);

        Boolean result = courseRegistrationUtils.hasRegistrationByStudentIdAndCourseId(studentId, courseId);

        assertTrue(result);
    }

    @Test
    public void testHasRegistrationByStudentIdAndCourseId_CourseTypeIsPrimary_ReturnsTrue() throws UserNotFoundException, CourseNotExistsInCatalogException {
        Integer studentId = 1;
        Integer courseId = 1;
        CourseType courseType = CourseType.PRIMARY;

        when(studentUtils.isStudentExistsById(studentId)).thenReturn(true);
        when(courseUtils.isCourseExistsInCatalog(courseId)).thenReturn(true);
        when(courseUtils.getCourseTypeByCourseId(courseId)).thenReturn(courseType);
        when(courseRegistrationRepository.existsByStudentIdAndPrimaryCourse1Id(studentId, courseId)).thenReturn(true);

        Boolean result = courseRegistrationUtils.hasRegistrationByStudentIdAndCourseId(studentId, courseId);

        assertTrue(result);
    }

    @Test
    public void testHasRegistrationByStudentIdAndCourseId_CourseTypeIsPrimary_ReturnsFalse() throws UserNotFoundException, CourseNotExistsInCatalogException {
        Integer studentId = 1;
        Integer courseId = 1;
        CourseType courseType = CourseType.PRIMARY;

        when(studentUtils.isStudentExistsById(studentId)).thenReturn(true);
        when(courseUtils.isCourseExistsInCatalog(courseId)).thenReturn(true);
        when(courseUtils.getCourseTypeByCourseId(courseId)).thenReturn(courseType);
        when(courseRegistrationRepository.existsByStudentIdAndPrimaryCourse1Id(studentId, courseId)).thenReturn(false);

        Boolean result = courseRegistrationUtils.hasRegistrationByStudentIdAndCourseId(studentId, courseId);

        assertFalse(result);
    }
}

