package com.codingbrothers.futurimages.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.HibernateValidator;

public abstract class ValidatingInterceptor implements MethodInterceptor {

	protected final ExecutableValidator executableValidator;

	public ValidatingInterceptor(MessageInterpolator messageInterpolator) {
		executableValidator = Validation.byProvider(HibernateValidator.class).configure()
				.messageInterpolator(messageInterpolator).buildValidatorFactory().getValidator().forExecutables();
	}

	protected abstract Object handleViolations(MethodInvocation invocation, Set<ConstraintViolation<Object>> violations);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Set<ConstraintViolation<Object>> violations = executableValidator.validateParameters(invocation.getThis(),
				invocation.getMethod(), invocation.getArguments(), getGroups());
		if (violations.isEmpty()) {
			return invocation.proceed();
		} else {
			return handleViolations(invocation, violations);
		}
	}

	protected Class<?>[] getGroups() {
		return new Class[] { javax.validation.groups.Default.class };
	}
}
