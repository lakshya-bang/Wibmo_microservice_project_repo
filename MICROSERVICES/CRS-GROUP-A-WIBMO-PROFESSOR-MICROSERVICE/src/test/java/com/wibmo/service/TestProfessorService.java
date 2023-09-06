/**
 * 
 */
package com.wibmo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wibmo.entity.Professor;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.repository.ProfessorRepository;

public class TestProfessorService {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorServiceImpl professorUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProfessorById_ProfessorExists_ReturnsProfessor() throws UserNotFoundException {
        Integer professorId = 1;
        Professor expectedProfessor = new Professor();

        when(professorRepository.existsById(professorId)).thenReturn(true);
        when(professorRepository.findAllByProfessorIdIn(Set.of(professorId))).thenReturn(List.of(expectedProfessor));

        Professor result = professorUtils.getProfessorById(professorId);

        assertEquals(expectedProfessor.getProfessorId(), result.getProfessorId());
    }

    @Test
    public void testGetProfessorById_ProfessorDoesNotExist_ThrowsUserNotFoundException() {
        Integer professorId = 1;

        when(professorRepository.existsById(professorId)).thenReturn(false);

        try {
        	professorUtils.getProfessorById(professorId);
        }
        catch(Exception e) {
        	assertTrue(e.getMessage().contains("NOT Found."));
        }
    }

    @Test
    public void testGetProfessorIdToProfessorMap_ValidProfessorIds_ReturnsProfessorMap() {
        Set<Integer> professorIds = Set.of(1, 2);
        Professor professor1 = new Professor(1,null,null,null,null);
        Professor professor2 = new Professor(2,null,null,null,null);

        when(professorRepository.findAllByProfessorIdIn(professorIds)).thenReturn(List.of(professor1, professor2));

        Map<Integer, Professor> result = professorUtils.getProfessorIdToProfessorMap(professorIds);

        assertEquals(2, result.size());
        assertTrue(result.containsKey(1));
        assertTrue(result.containsKey(2));
        assertEquals(professor1, result.get(1));
        assertEquals(professor2, result.get(2));
    }

    @Test
    public void testAdd_ProfessorAddedSuccessfully() {
        Professor professor = new Professor();

        professorUtils.add(professor);

        verify(professorRepository).save(professor);
    }
}
