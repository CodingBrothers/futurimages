package com.codingbrothers.futurimages.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public abstract class ValidatingInterceptor implements MethodInterceptor {

	@Target({ METHOD })
	@Retention(RUNTIME)
	public @interface MethodGroupSequence {

		Class<?>[] value();
	}

	private final ExecutableValidator executableValidator;
	private final boolean validateParameters;
	private final boolean validateReturnType;

	public ValidatingInterceptor(ExecutableValidator executableValidator, boolean validateParameters,
			boolean validateReturnType) {
		this.executableValidator = Objects.requireNonNull(executableValidator);
		this.validateParameters = validateParameters;
		this.validateReturnType = validateReturnType;
	}

	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?>[] groups = getGroups(invocation);

		Set<ConstraintViolation<Object>> violations = null;

		if (validateParameters) {
			violations = executableValidator.validateParameters(invocation.getThis(), invocation.getMethod(),
					invocation.getArguments(), groups);
		}

		if (violations == null || violations.isEmpty()) {
			Object result = invocation.proceed();
			if (validateReturnType) {
				violations = executableValidator.validateReturnValue(invocation.getThis(), invocation.getMethod(),
						invocation.getArguments(), groups);
			}
			if (violations == null || violations.isEmpty()) {
				return result;
			} else {
				return handleReturnTypeViolations(invocation, violations);
			}
		} else {
			return handleParametersViolations(invocation, violations);
		}
	}

	protected Class<?>[] getGroups(MethodInvocation invocation) {
		if(invocation.getMethod().isAnnotationPresent(MethodGroupSequence.class)) {
			return invocation.getMethod().getAnnotation(MethodGroupSequence.class).value();
		} else {
			return new Class[0]; // i.e. use the default group
		}
	}

	protected Object handleParametersViolations(MethodInvocation invocation, Set<ConstraintViolation<Object>> violations) {
		throw new UnsupportedOperationException();
	}

	protected Object handleReturnTypeViolations(MethodInvocation invocation, Set<ConstraintViolation<Object>> violations) {
		throw new UnsupportedOperationException();
	}
}
