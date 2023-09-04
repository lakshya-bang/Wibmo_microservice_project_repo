package com.wibmo.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.wibmo.entity.Professor;

/**
 * Utility class on Professor
 */
public class ProfessorUtils {

	public static Map<Integer, Professor> getProfessorIdToProfessorMap(
			Collection<Professor> professors) {
		return professors
				.stream()
				.collect(Collectors.toMap(
						Professor::getProfessorId,
						Function.identity()));
	}
}
