package com.codingbrothers.futurimages.apiv1.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// TODO - make LengthELValidator working on AppEngine
public class LengthELValidator implements ConstraintValidator<LengthEL, String> {

	private static final String VALIDATED_VALUE_NAME = "validatedValue";

//	/**
//	 * Factory for creating EL expressions
//	 */
//	private static final ExpressionFactory expressionFactory;
//	static {
//		expressionFactory = ExpressionFactory.newInstance();
//	}

	private String minExpr;
	private String maxExpr;

	@Override
	public void initialize(LengthEL constraintAnnotation) {
		minExpr = constraintAnnotation.min();
		maxExpr = constraintAnnotation.max();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		Boolean isValid = Boolean.TRUE;

//		if (minExpr != null && !minExpr.isEmpty()) {
//			isValid = evaluateExpr(minExpr, value);
//		}
//
//		if (Boolean.TRUE.equals(isValid) && maxExpr != null && !maxExpr.isEmpty()) {
//			isValid = evaluateExpr(minExpr, value);
//		}

		return isValid != null && isValid;
	}

//	private static Boolean evaluateExpr(String expr, String validatedValue) {
//		try {
//			SimpleELContext elContext = new SimpleELContext();
//			ValueExpression valueExpression = expressionFactory.createValueExpression(validatedValue, String.class);
//			elContext.setVariable(VALIDATED_VALUE_NAME, valueExpression);
//			valueExpression = expressionFactory.createValueExpression(elContext, expr, String.class);
//			return (Boolean) valueExpression.getValue(elContext);
//		} catch (Exception e) {
//			return null;
//		}
//	}

}
