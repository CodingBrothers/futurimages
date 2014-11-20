package com.codingbrothers.futurimages.apiv1.util;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnyOfValidator implements ConstraintValidator<AnyOf, String> {

	private List<String> validValues;

	@Override
	public void initialize(AnyOf constraintAnnotation) {
		validValues = Arrays.asList(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || validValues.contains(value)) {
			return true;
		} else {
			return false;
		}
	}

}
