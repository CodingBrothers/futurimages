package com.codingbrothers.futurimages.apiv1;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.codingbrothers.futurimages.config.FuturimagesApiV1Module;

@Aspect
public class EndpointsValidationAspect {

	private static final ExecutableValidator validator = FuturimagesApiV1Module.getValidationFactory().getValidator()
			.forExecutables();

	@Pointcut("call(public com.codingbrothers.futurimages.apiv1.Response com.codingbrothers.futurimages.apiv1.*V1.* (..))")
	public void endpoint() {

	}

	@Around("endpoint() && target(api)")
	public Response validateEndpointParameters(Object api, ProceedingJoinPoint pjp) throws Throwable {
		Set<ConstraintViolation<Object>> violations = validator.validateParameters(api,
				((MethodSignature) pjp.getSignature()).getMethod(), pjp.getArgs());
		
		if(violations.isEmpty()) {
			return (Response) pjp.proceed();
		} else {
			return Responses.createClientError(violations, null);
		}
	}

}
