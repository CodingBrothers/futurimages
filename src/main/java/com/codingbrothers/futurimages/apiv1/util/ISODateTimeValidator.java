package com.codingbrothers.futurimages.apiv1.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class ISODateTimeValidator implements ConstraintValidator<ISODateTime, String> {

	@Override
	public void initialize(ISODateTime constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		try { // it might not really be a fast solution - consider replacing it with simple regex 
			DateTime.parse(value, ISODateTimeFormat.dateTimeParser());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
